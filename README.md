Pearlfish for Java
==================

Specification-by-Example crossed with Approval Testing.

Pearlfish may be useful for you if:

 * You can change production code very rapidly, so that a document-first Specification by Example process
   adds unhelpful overhead. Or...
 * Acceptance is qualitative rather than precise. Or...
 * The behaviour you want to capture involves a lot of numerical data, that can be understood
   better through visualisation than text. Or...
 * You are working exploratively and want to transition the results of exploratory programming to
   production.

Overview
--------

There are two ways of using Pearlfish - as an [Approval Testing](http://www.approvaltests.com)
library that outputs documents suitable for [Specification by Example](http://martinfowler.com/bliki/SpecificationByExample.html)
or as a Specification by Example library that checks results by Approval Testing.

At a very high level, the Approval Testing workflow is:

 1. Invoke the system under test and build up a data structure that stores the inputs and outputs of
    all the invocations.

 2. Pass the data to the approval testing library for approval.  The library will write the data to a file (known as the
    "received file") and compare it to an existing approved version (known as the "approved file").
    Initially there will be no approved file, so the library will fail the test.

 3. Change the system until the received file contains the output values that you expect.

 4. Approve the the received file by renaming it have the name of the the approved file.

 5. From now on, the approval test library will fail the test if the received file is different from
    the approved file.

 6. If the differences that approval test library detects are ok, approve the received file again.

Pearlfish augments this workflow by letting you insert the data into documents in
[Markdown](http://daringfireball.net/projects/markdown/) and other formats, so that you can add
explanatory text or visualise the data. Formatting is controlled by [Mustache](http://mustache.github.io/) templates.
If you don't write a template for a test, Pearlfish will save the data in a format
(currently [YAML](http://www.yaml.org))  that is easy to read and diff and clearly shows the
structure of the data that a template must follow.

The Markdown documents that Pearlfish tests generate can be translated to other formats -- HTML
or PDF for example -- with existing tools such as [Pandoc](http://johnmacfarlane.net/pandoc/index.html).

To follow the usual document-first workflow of Specification by Example, write the Markdown document
for a test first and save it as the approved file. Translate that document into a template by replacing
the data with template declarations. Then capture data from the system and pass it to Pearlfish.
At first, the test will fail.  Modify the system until the test passes.

Requirements
------------

Pearlfish requires:

 * Java 1.7 or above

Pearlfish provides adaptors that integrate with [JUnit 4](http://www.junit.org). 
You can use Pearlfish with other test frameworks by using the same extension points as the JUnit adaptor.

To build from source, you need:

 * Ant 1.8 or above
 * Bash


Build
-----

Cd to the java/ directory and run `./build`.  JAR files will be created in the java/out/ directory.


A Short Example
---------------

In this example will use Pearlfish and [JUnit](http://www.junit.org) to write a very simple test for an
[RPN calculator](http://en.wikipedia.org/wiki/Reverse_Polish_notation). 

We will start by doing approval testing and then generate Specification by Example documentation
from the approval tests.

The tests will control the calculator with the following API:

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~java
public class Calculator {
    public void push(BigInteger operand);
    public void push(long operand);
    public BigInteger pop();
    public void add();
    ... other operations...
}
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

To test the calculator with Pearlfish and JUnit:

 1. Create an ApprovalRule as a field of the test object:

    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~java
    public class BasicArithmeticTest {
        public @Rule ApprovalRule<Object> approval = new ApprovalRule<Object>("test", Formats.MARKDOWN);

        ...
    }
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    The ApprovalRule constructor takes two parameters: the name of the directory that contains the source
    code of our tests and the format in which we will record the results of the test
    (in this case, Markdown).

 2. Perform some calculations with the calculator and build up a data structure that stores inputs and
    calculated results.

    Pearlfish provides some convenience classes and factory functions in the `Results` class
    that combine inputs and outputs with an explanatory name and group related scenarios, but you don't
    have to use them.  You can use any class that can be introspected by
    [JMustache](https://github.com/samskivert/jmustache).

    For this example we'll use the convenience functions in the Results class and therefore need only
    define a class to hold the two input operands.

    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~java
    public static class Operands {
        public int x, y;

        public Operands(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    With that we can write a method to perform a calculation and return information about the
    inputs and outputs:

    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~java
    Scenario<Operands, BigInteger> addition(final String description, int x, int y)
    {
        calculator.push(x);
        calculator.push(y);
        calculator.add();
        return Results.scenario(description, new Operands(x, y), calculator.pop());
    }
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    Our test will call addition multiple times to exercise different cases, using the functions of the
    Results class to group the results into related sections before passing them all to the
    ApprovalRule to be checked.

    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~java
    @Test
    public void addition() throws IOException {
        approval.check(Results.results(
            addition("simple add", 1, 2),
            addition("zero left", 0, 2),
            addition("zero right", 1, 0),
            addition("zero both", 0, 0)));
    }
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 3. Because we have not written a template, the ApprovalRule saves the results in YAML format.
    We can use this file to see the structure of the data that will be passed to our template.

    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~yaml
    results:
    - input: {x: 1, y: 2}
      name: simple add
      output: 3
    - input: {x: 0, y: 2}
      name: zero left
      output: 2
    ... etc ...
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 4. If we're just using approval testing for developer-focused regression testing, this might
    be enough.  But if we want to document system behaviour for other (non-technical) stakeholders
    we can write a template to organise the data clearly in tables and add explanatory text.

    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Addition
    ========

    The calculator performs basic addition.

    | Scenario   | X            | Y            | X + Y      |
    |------------|-------------:|-------------:|-----------:|
    {{#results}}
    | {{name}}   | {{input.x}}  | {{input.y}}  | {{output}} |
    {{/results}}
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    Pearlfish uses the [Mustache](http://mustache.github.io/) template language.

 5. Running the test again generates the received file in Markdown format. Pearlfish ensures that
    the tabular data is easy to read and differences are easy to see.

    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Addition
    ========

    The calculator performs basic addition.

    | Scenario     | X            | Y            | X + Y      |
    |--------------|-------------:|-------------:|-----------:|
    | simple add   | 1            | 2            | 3          |
    | zero left    | 0            | 2            | 2          |
    | zero right   | 1            | 0            | 1          |
    | zero both    | 0            | 0            | 0          |
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 6. As we implement the calculator we rerun the test.  When the results are correct, we
    approve the received file.  From now on, it acts as a regression test.

 7. We can continue to add features to our calculator or increase test coverage. As we generate
    more results we can use the Results class to group them into sections...

    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~java
    @Test
    public void addition() throws IOException {
        approval.check(results(
                section("basic",
                        addition("simple add", 1, 2),
                        addition("zero left", 0, 2),
                        addition("zero right", 1, 0),
                        addition("zero both", 0, 0)),
                section("negative",
                        addition("negative left", -4, 2),
                        addition("negative right", 5, -4),
                        addition("negative left and zero", -1, 0),
                        addition("zero and negative right", 0, -6),
                        addition("both negative", -4, -9)),
                section("large",
                        addition("large addition", Long.MAX_VALUE, Long.MAX_VALUE),
                        addition("large negative numbers", Long.MIN_VALUE, Long.MIN_VALUE))));
    }
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    ...and extend our template to match:

    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Addition
    ========

    The calculator performs basic addition.

    | Scenario   | X            | Y            | X + Y      |
    |------------|-------------:|-------------:|-----------:|
    {{#basic}}
    | {{name}}   | {{input.x}}  | {{input.y}}  | {{output}} |
    {{/basic}}


    Addition supports negative numbers.

    | Scenario   | X            | Y            | X + Y      |
    |------------|-------------:|-------------:|-----------:|
    {{#negative}}
    | {{name}}   | {{input.x}}  | {{input.y}}  | {{output}} |
    {{/negative}}


    Addition can result in integers larger than 32-bits in size.

    | Scenario   | X            | Y            | X + Y      |
    |------------|-------------:|-------------:|-----------:|
    {{#large}}
    | {{name}}   | {{input.x}}  | {{input.y}}  | {{output}} |
    {{/large}}
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    

The full code of example is [included in the Pearlfish tests](test/com/natpryce/pearlfish/example/calculator/), including [the Markdown document generated from the final template above](test/com/natpryce/pearlfish/example/calculator/BasicArithmeticTest.addition-approved.md).


More Information
----------------

You can read more about Approval Testing at http://approvaltests.com.  There are some introductory
screencasts that demonstrate how to apply the technique to new and legacy code.

You can read more about [Specification by Example on Wikipedia](http://en.wikipedia.org/wiki/Specification_by_example),
which links to several books and tools.

You can learn more about the Mustache template language at http://mustache.github.io/

News
----

Follow [@pearlfishlib](https://twitter.com/pearlfishlib) on Twitter to receive occasional news.

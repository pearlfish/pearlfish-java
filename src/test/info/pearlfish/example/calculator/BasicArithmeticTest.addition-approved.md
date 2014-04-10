Addition
========

The calculator performs basic addition.

| Scenario     | X            | Y            | X + Y      |
|--------------|-------------:|-------------:|-----------:|
| simple add   | 1            | 2            | 3          |
| zero left    | 0            | 2            | 2          |
| zero right   | 1            | 0            | 1          |
| zero both    | 0            | 0            | 0          |


Addition supports negative numbers.

| Scenario                  | X            | Y            | X + Y      |
|---------------------------|-------------:|-------------:|-----------:|
| negative left             | \-4          | 2            | \-2        |
| negative right            | 5            | \-4          | 1          |
| negative left and zero    | \-1          | 0            | \-1        |
| zero and negative right   | 0            | \-6          | \-6        |
| both negative             | \-4          | \-9          | \-13       |


Addition can result in integers larger than 32-bits in size.

| Scenario                 | X                      | Y                      | X + Y                  |
|--------------------------|-----------------------:|-----------------------:|-----------------------:|
| large addition           | 9223372036854775807    | 9223372036854775807    | 18446744073709551614   |
| large negative numbers   | \-9223372036854775808  | \-9223372036854775808  | \-18446744073709551616 |

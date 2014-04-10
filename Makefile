
groupid=info.pearlfish
package=pearlfish
version:=$(shell git describe --tags --always --dirty=-local --match='v*' | sed -e 's/^v//')
release=$(package)-$(version)

outdir=out
srcdir_main=src/main
srcdir_test=src/test

JAR=jar
JAVAC=javac
JAVA=java
JAVADOC=javadoc

JAVACFLAGS=-g

java_src=$(shell find $1 -type f -and -name '*.java')
java_resources=$(shell find $1 -type f -and -not -name '*.java')
topath=$(subst $(eval) ,:,$1)
classpath=$(patsubst %,-classpath %,$(call topath,$(filter-out %-sources.jar,$(filter %.jar,$^))))

src_main:=$(call java_src,$(srcdir_main))
resources_main:=$(call java_resources,$(srcdir_main))
src_test:=$(call java_src,$(srcdir_test))
resources_test:=$(call java_resources,$(srcdir_test))

published_jars = $(outdir)/$(release).jar $(outdir)/$(release)-sources.jar $(outdir)/$(release)-javadoc.jar
test_jars = $(outdir)/$(release)-test.jar

published_files = $(published_jars) $(outdir)/$(release).pom
published_signatures = $(published_files:%=%.asc)

all: tested jars

tested: $(outdir)/junit-report.txt

jars: $(published_jars) $(test_jars)

include libs/main.mk
include libs/test.mk
include libs/build.mk

libs/%.mk: %.dependencies
	rm -rf libs/$*
	mkdir -p libs/$*
	tools/sm-download $< libs/$*
	echo 'libs_$*=$$(filter-out %-source.jar,$$(wildcard libs/$*/*.jar))' > $@

$(outdir)/$(release).jar: $(src_main) $(libs_main)
$(outdir)/$(release).jar: $(resources_main:$(srcdir_main)/%=$(outdir)/$(release)/%)
$(outdir)/$(release).jar: $(libs_main)

$(outdir)/$(release)-test.jar: $(src_test)
$(outdir)/$(release)-test.jar: $(resources_test:$(srcdir_test)/%=$(outdir)/$(release)-test/%)
$(outdir)/$(release)-test.jar: $(outdir)/$(release).jar $(libs_main) $(libs_test)

%.jar:
	@mkdir -p $*/
	$(JAVAC) $(JAVACFLAGS) $(classpath) -d $*/ $(filter %.java,$^)
	$(JAR) -cf$(JARFLAGS) $@ -C $* .

$(outdir)/junit-report.txt: TESTS=$(subst /,.,$(filter %Test,$(patsubst $(srcdir_test)/%.java,%,$(src_test))))
$(outdir)/junit-report.txt: $(outdir)/$(release).jar $(outdir)/$(release)-test.jar $(libs_main) $(libs_test)
	$(JAVA) $(classpath) org.junit.runner.JUnitCore $(TESTS) | tee $@

$(outdir)/$(release)/%: $(srcdir_main)/%
	@mkdir -p $(dir $@)
	cp $< $(dir $@)

$(outdir)/$(release)-test/%: $(srcdir_test)/%
	@mkdir -p $(dir $@)
	cp $< $(dir $@)


$(outdir)/$(release)-sources.jar: $(src_main)
	$(JAR) cf $@ -C $(srcdir_main) .

$(outdir)/$(release)-javadoc.jar: $(outdir)/$(release)-javadoc/index.html
	$(JAR) cf $@ -C $(dir $<) .

$(outdir)/$(release)-javadoc/index.html: $(src_main) $(libs_main)
	@mkdir -p $(dir $@)
	$(JAVADOC) -d $(dir $@) $(classpath) $(src_main)

$(outdir)/$(release).pom: main.dependencies $(published_jars)
	@mkdir -p $(dir $@)
	tools/sm-pom mvn:$(groupid):$(package):jar:$(version) main.dependencies $(dir $@)
	mv $@ $@-tmp
	xsltproc tools/pom.xslt $@-tmp | xmllint --format --nsclean - > $@

$(outdir)/%.asc: $(outdir)/%
	gpg --detach-sign --armor $<

clean:
	rm -rf $(outdir)

distclean: clean
	rm -rf libs/

again: clean all

published: $(published_files) $(published_signatures)
	publish-to-bintray $(groupid) $(package) $(version) $^

ifeq "$(origin version)" "command line"
tagged:
	git tag -s v$(version) -m "tagging version $(version)"
else
tagged:
	@echo set the version to tag on the command line
	@false
endif

.PHONY: all jars tested clean distclean published tagged

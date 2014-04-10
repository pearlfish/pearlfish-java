/**
 * The core Pearlfish API and interfaces that define extension points.
 * <p>
 * Tests usually do not use this API directly, but use an adaptor API that uses the extension
 * points to adapt the behaviour of the Approver to the needs of a specific test framework.
 * For example, the {@link info.pearlfish.adaptor.junit.ApprovalRule} class adapts
 * Pearlfish to work with JUnit.
 * </p>
 */
package info.pearlfish;

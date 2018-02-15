package com.wandera.ci.tests

import com.lesfurets.jenkins.unit.BasePipelineTest
import com.lesfurets.jenkins.unit.MethodCall
import org.junit.Before
import org.junit.Test

class TestExampleJob extends BasePipelineTest {

    @Override
    @Before
    void setUp() throws Exception {
        scriptRoots += 'src/test/jenkins/'
        super.setUp()
        def scmBranch = "feature_test"
        helper.registerAllowedMethod("sh", [Map.class], {c -> 'bcc19744'})
        binding.setVariable('scm', [
                        $class                           : 'GitSCM',
                        branches                         : [[name: scmBranch]]
        ])
    }

    @Test
    void should_execute_without_errors() throws Exception {
        def script = runScript("job/exampleJob1.jenkins")
//        script.run()
        printCallStack()
        assertJobStatusSuccess()
    }

    @Test
    void should_print_property_value() {
        def script = runScript('job/exampleJob.jenkins')
        script.execute()

        def value = 'value'
        org.junit.Assert.assertTrue(helper.callStack.findAll { call ->
            call.methodName == 'println'
        }.any { call ->
            MethodCall.callArgsToString(call).contains(value)
        })
    }
}

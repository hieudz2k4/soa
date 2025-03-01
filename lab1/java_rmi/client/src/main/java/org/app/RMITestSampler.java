package org.app;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMITestSampler extends AbstractJavaSamplerClient {
  @Override
  public Arguments getDefaultParameters() {
    Arguments defaultParameters = new Arguments();
    defaultParameters.addArgument("id", "00288978-506e-40e1-93c8-954390f3032c");
    defaultParameters.addArgument("quantity", "2");
    return defaultParameters;
  }

  @Override
  public SampleResult runTest(JavaSamplerContext context) {
    SampleResult result = new SampleResult();
    result.sampleStart();
    try {
      Registry registry = LocateRegistry.getRegistry("192.168.33.10", 1099);
      OrderService service = (OrderService) registry.lookup("OrderService");

      String productId = context.getParameter("id", "00288978-506e-40e1-93c8-954390f3032c");
      String quantityStr = context.getParameter("quantity", "1");
      int quantity = Integer.parseInt(quantityStr);

      Double response = service.calculateTotal(productId, quantity);

      result.sampleEnd();
      result.setSuccessful(true);
      result.setResponseData(response.toString(), "UTF-8");
      result.setResponseMessage("RMI call executed successfully");
      result.setResponseCodeOK();
    } catch (Exception e) {
      result.sampleEnd();
      result.setSuccessful(false);
      result.setResponseData(e.toString(), "UTF-8");
      result.setResponseMessage("Exception: " + e.getMessage());
      result.setResponseCode("500");
    }
    return result;
  }
}

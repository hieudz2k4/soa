package org.app;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.app.order.OrderConfirmation;
import org.app.order.OrderService;

public class ThriftTestSampler extends AbstractJavaSamplerClient {
  @Override
  public Arguments getDefaultParameters() {
    Arguments defaultParameters = new Arguments();
    defaultParameters.addArgument("host", "192.168.33.10");
    defaultParameters.addArgument("port", "9090");
    defaultParameters.addArgument("productId", "P12345");
    defaultParameters.addArgument("quantity", "5");
    return defaultParameters;
  }

  @Override
  public SampleResult runTest(JavaSamplerContext context) {
    SampleResult result = new SampleResult();
    result.sampleStart();

    String host = context.getParameter("host", "192.168.33.10");
    int port = Integer.parseInt(context.getParameter("port", "9090"));
    String productId = context.getParameter("productId", "P12345");
    int quantity = Integer.parseInt(context.getParameter("quantity", "5"));

    TTransport transport = null;
    try {
      transport = new TSocket(host, port);
      transport.open();

      TProtocol protocol = new TBinaryProtocol(transport);
      OrderService.Client client = new OrderService.Client(protocol);

      OrderConfirmation response = client.calculateTotal(productId, quantity);

      result.sampleEnd();
      result.setSuccessful(true);
      result.setResponseData(response.toString(), "UTF-8");
      result.setResponseMessage("Thrift call executed successfully");
      result.setResponseCodeOK();
    } catch (TException e) {
      result.sampleEnd();
      result.setSuccessful(false);
      result.setResponseData(e.toString(), "UTF-8");
      result.setResponseMessage("Exception: " + e.getMessage());
      result.setResponseCode("500");
    } finally {
      if (transport != null) {
        transport.close();
      }
    }
    return result;
  }
}

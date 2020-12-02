Assignment2 XML-RPC-interaction

Get familiar with XML-RPC: a simple server-client-interaction to calculate numbers

Getting Started: 

Before you build with ant, go to directory /src/de/cmlab/ubicomp/client/ and open file JavaClient.java. 

There you need to enter the IP-Address of the server at line 19:

    //TODO: server url with port and your ip: „http://{my IP-Address}:{Port}/sample/“
    XmlRpcClient server = new XmlRpcClient("http://111.111.111.11:80/sample/");

Build project with ant:

After "ant" or "ant compile", you need to run targets run.client and run.server in separate terminals. 

Further explanations of available targets see comments in build.xml.

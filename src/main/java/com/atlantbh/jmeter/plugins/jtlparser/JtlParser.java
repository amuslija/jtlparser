package com.atlantbh.jmeter.plugins.jtlparser;

import com.atlantbh.jmeter.plugins.jtlparser.model.jtl.*;
import com.atlantbh.jmeter.plugins.jtlparser.model.jtl.ThreadGroup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by adnan on 12/12/15.
 */
public class JtlParser {
    ///////////////////////////
    //PRIVATE HELPER METHODS//
    /////////////////////////
    private HashMap<String,ThreadGroup> threadGroups;

    private String getThreadGroupName(String name){
        String threadGroupName = name.substring(0, name.lastIndexOf(" "));
        return threadGroupName;
    }

    private String getThreadIteration(String name){
        String threadIteration = name.substring(name.lastIndexOf(" ") + 1);
        return threadIteration;
    }

    private Sampler createSampler(Node samplerNode){
        Element element = (Element) samplerNode;
        Sampler sampler = new Sampler();
        String samplerIteration = getThreadIteration(element.getAttribute("tn"));

        sampler.setSamplerName(samplerNode.getNodeName());
        sampler.setName(element.getAttribute("lb") + " " + samplerIteration );
        // sampler.setResponseCode(element.getAttribute("rc"));
        // sampler.setResponseMessage(element.getAttribute("rm"));
        sampler.setTime(element.getAttribute("t"));
        return sampler;
    }

    private ThreadGroup createThreadGroup(String threadName){
        ThreadGroup newThreadGroup = new ThreadGroup();
        newThreadGroup.setThreadName(threadName);
        threadGroups.put(threadName, newThreadGroup);
        return newThreadGroup;
    }

    private AssertionResult createAssertionResult(Node assertNode){
        AssertionResult assertionResult = new AssertionResult();
        NodeList assertChildNodes = assertNode.getChildNodes();

        //Get AssertionResult details
        for(int i = 0; i < assertChildNodes.getLength(); i++) {
            Node node = assertChildNodes.item(i);
            if (node.getNodeName() == "name")
                assertionResult.setName(node.getTextContent());
            if (node.getNodeName() == "failure")
                assertionResult.setFailure(node.getTextContent());
            if (node.getNodeName() == "error")
                assertionResult.setError(node.getTextContent());
            if (node.getNodeName() == "failureMessage")
                assertionResult.setFailureMessage(node.getTextContent());
        }
        return assertionResult;
    }

    ///////////////////
    //PUBLIC METHODS//
    /////////////////

    public JtlParser(){
        super();
        threadGroups  = new HashMap<String, ThreadGroup>();
    }

    // Get ThreadGroup from a HashMap with key which equals ThreadGroup name.
    public ThreadGroup getThreadGroup(String threadGroupName) {
        Object item = threadGroups.get(threadGroupName);
        if(item instanceof ThreadGroup){
            return (ThreadGroup) item;
        }
        return null;
    }

    public HashMap<String, ThreadGroup> getThreadGroupsMap(){
        return threadGroups;
    }

    public ArrayList<ThreadGroup> getThreadGroups(){
        return new ArrayList<ThreadGroup>(threadGroups.values());
    }

    public NodeList getStartNode(String fileLocation) throws Exception{
        File jtlFile = new File(fileLocation);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        NodeList nodeList = null;
        try{
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document jtl = dBuilder.parse(jtlFile);
            jtl.getDocumentElement().normalize();
            nodeList = jtl.getElementsByTagName("testResults");
        } catch (ParserConfigurationException e) {
            e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            e.getMessage();
            e.printStackTrace();
        }

        if (nodeList == null) {
            throw new Exception("Node list has not been populated!");
        } else {
            return nodeList;
        }
    }

    public void parseJtl(NodeList nodeList, Sampler parentSampler) throws IOException, ParserConfigurationException {
        for (int i =0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            Sampler sampler = null;
            boolean foundAssertion = false;

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String samplePattern = "^.*[sS]ample";
                Element element = (Element) node;

                if (node.getNodeName().matches(samplePattern)){

                    String threadName = getThreadGroupName(element.getAttribute("tn"));
                    sampler = createSampler(node);

                    if(getThreadGroup(threadName) != null){
                        ThreadGroup threadGroup = getThreadGroup(threadName);
                        threadGroup.addSampler(sampler);
                    }else{
                        ThreadGroup threadGroup = createThreadGroup(threadName);
                        threadGroup.addSampler(sampler);
                    }
                }
                if(node.getNodeName() == "assertionResult") {
                    parentSampler.addAssertionResult(createAssertionResult(node));
                    foundAssertion = true;
                }
            }

            if(node.hasChildNodes() && !foundAssertion){
                parseJtl(node.getChildNodes(), sampler);
            }
        }
    }
}


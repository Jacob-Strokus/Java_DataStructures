import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collections;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;
import org.junit.rules.ExpectedException;






public class P3Tester {
  public static void main(String args[]){
      org.junit.runner.JUnitCore.main("P3Tester");
    }
   
  
  static final double ERROR = 0.000001;
  //*************************************Interface Tests*************** 
 public DecisionTree<String> createDecisionTree() {
        DecisionTree<String> dTree = new DecisionTree<>();
        dTree.setTree("D",null,null);
        DecisionTree<String> fTree = new DecisionTree<>();
        fTree.setTree("F",null,null);
        DecisionTree<String> gTree = new DecisionTree<>();
        gTree.setTree("G",null,null);
        DecisionTree<String> hTree = new DecisionTree<>();
        hTree.setTree("H",null,null);
        DecisionTree<String> eTree = new DecisionTree<>();
        eTree.setTree("E",fTree,gTree);
        DecisionTree<String> bTree = new DecisionTree<>();
        bTree.setTree("B",dTree,eTree);
        DecisionTree<String> empty = new DecisionTree<>();
        DecisionTree<String> cTree = new DecisionTree<>();
        cTree.setTree("C",empty,hTree);
        DecisionTree<String> aTree = new DecisionTree<>();
         aTree.setTree("A",bTree,cTree);
         return aTree;
      }
 @Test(timeout=1000)public void DecisionTree_setTree_00() { 
	  
    BinaryNode<String> node = new BinaryNode<>("A");
    DecisionTree<String> node1 = createDecisionTree();
   
  assertEquals("DecisionTree setTree method is not working properly", node.getData(), node1.getRootNode().getData());
  
  }
  
  
 
  @Test(timeout=1000) public void DecisionTree_moveToNo_test_00() {
	  
	  DecisionTree<String> node1 = createDecisionTree();
	node1.resetCurrentNode();
	  node1.moveToNo();
	   
	  assertEquals("DecisionTree moveToNo method is not working properly", "B", node1.getCurrentNode().getData());
	  node1.moveToNo();
	  assertEquals("DecisionTree moveToNo method is not working properly", "D", node1.getCurrentNode().getData());
	
  } 
@Test(timeout=1000) public void DecisionTree_moveToYes_test_00() {
	  
	  DecisionTree<String> node1 = createDecisionTree();
	node1.resetCurrentNode();
	  node1.moveToYes();
	   
	  assertEquals("DecisionTree moveToYes method is not working properly", "C", node1.getCurrentNode().getData());
	  node1.moveToYes();
	  assertEquals("DecisionTree moveToYes method is not working properly", "H", node1.getCurrentNode().getData());
	
  } 
  
@Test(timeout=1000) public void DecisionTree_moveToYesNO_test_00() {
	  
	  DecisionTree<String> node1 = createDecisionTree();
	node1.resetCurrentNode();
	   node1.moveToNo();
	   node1.moveToYes();
	   node1.moveToNo();
	  assertEquals("DecisionTree moveToYes method is not working properly", "F", node1.getCurrentNode().getData());
	
} 
  @Test(timeout=1000) public void DecisionTree_SetResponse_test_01() {
	  
	  DecisionTree<String> node1 = createDecisionTree();
		node1.resetCurrentNode();
		   node1.moveToNo();
		   node1.moveToYes();
		   node1.moveToNo();
		   node1.setResponses("L", "M");
		   node1.resetCurrentNode();
		   node1.moveToNo();
		   node1.moveToYes();
		   node1.moveToNo();
		   node1.moveToNo();
		   assertEquals("DecisionTree moveToYes method is not working properly", "L", node1.getCurrentNode().getData());
	 } 
  @Test(timeout=1000) public void DecisionTree_isAnswer_test_00() {
	  
	  DecisionTree<String> node1 = createDecisionTree();
	  node1.resetCurrentNode();
	  assertEquals("DecisionTree isAnswer method is not working properly", false, node1.isAnswer()); 
	   node1.moveToNo();
	   node1.moveToYes();
	   node1.moveToNo();
	   assertEquals("DecisionTree isAnswer method is not working properly", true, node1.isAnswer()); 
	  
	
  } 
@Test(timeout=1000) public void DecisionTree_getNumberofNodes_test_00() {
	  
	  DecisionTree<String> node1 = createDecisionTree();
	  node1.resetCurrentNode();
	   
	  assertEquals("DecisionTree getNumberofNodes method is not working properly", 8, node1.getNumberOfNodes());
	  node1.moveToNo();
	  assertEquals("DecisionTree getNumberofNodes method is not working properly", 5, node1.getCurrentNode().getNumberOfNodes());
	
  } 

@Test(timeout=1000) public void DecisionTree_getHeight_test_00() {
	  
	  DecisionTree<String> node1 = createDecisionTree();
	  node1.resetCurrentNode();
	   
	  assertEquals("DecisionTree getHeight method is not working properly", 3, node1.getHeight());
	  node1.moveToYes();
	  assertEquals("DecisionTree getHeight method is not working properly", 1, node1.getCurrentNode().getHeight());
} 

@Test(timeout=1000) public void DecisionTree_Inheritance_test_00() {
	  
	  BinaryTreeInterface<String> node1 = createDecisionTree();
	  
	   
	  assertTrue("DecisionTree inheritance is not working properly", node1 instanceof DecisionTree);
	  assertTrue("DecisionTree inheritance is not working properly", node1 instanceof BinaryTree);
	  
} 
  @Test(expected = EmptyTreeException.class) 
  public void OneEndContainer_remove_08() {
  	  DecisionTree<Integer> f = new DecisionTree<>();
  	  f.getRootData();
  }
  
  
  
  //*******************************CovidHealthBuilder Tests************************

 
public DecisionTree<String> createCovidHealthTree() {
	CovidHealthBuilder hh = new CovidHealthBuilder("data2.txt");
	return (DecisionTree<String>) hh.getHealthTree();
}


@Test(timeout=1000) public void CovidHealthTree_moveToNoYes_test_00() {
	  
	  DecisionTree<String> node1 = createCovidHealthTree();
	  node1.resetCurrentNode();
	  node1.moveToYes();
	   
	  assertEquals("CovidHealthBuilder moveToYes method is not working properly", "workout?", node1.getCurrentNode().getData());
	  node1.moveToNo();
	  assertEquals("CovidHealthBuilder moveToNo method is not working properly", "unfit", node1.getCurrentNode().getData());
	
} 
 
String sep = System.getProperty("line.separator");
@Rule
public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();
@Test(timeout=1000) public void CovidHealthTree_Decide_test_00() {
	  
	// DecisionTree<String> node1 = createCovidHealthTree();
	 CovidHealthBuilder hh = new CovidHealthBuilder("data2.txt");
	 String expected1 = "Age >30?\nworkout?\nunfit\n\n"
             +"Satisfied by my intelligence?\n\n"; 
	    
	   systemInMock.provideLines("yes","no","yes");//
	   setCapture();
	   hh.decide();
	   String actual = getCapture();
	   unsetCapture(); 
	   String msg = String.format("expected = %s  is not equal to actual = %s", expected1, actual);
	  assertTrue(msg, expected1.equalsIgnoreCase(actual));
	
	  
} 

@Test(timeout=1000) public void CovidHealthTree_Decide_test_01() {
	  
	// DecisionTree<String> node1 = createCovidHealthTree();
	 CovidHealthBuilder hh = new CovidHealthBuilder("data2.txt");
	 String expected = "Age >30?\nfit\n\n"
	              +"Satisfied by my intelligence?\n\n";
	 
	   
	   systemInMock.provideLines("no","yes");
	   setCapture();
	   hh.decide();
	  String actual = getCapture();
	   unsetCapture(); 
	  
	      String msg = String.format("expected = %s  is not equal to actual = %s", expected, actual);
		  assertTrue(msg, expected.equalsIgnoreCase(actual));
}

@Test(timeout=1000) public void CovidHealthTree_learn_test_01() {
	  
	CovidHealthBuilder hh = new CovidHealthBuilder("data2.txt");
	 systemInMock.provideLines("no","no","unfit","eat unhealthy?");
	   setCapture();
	   hh.decide();
	   hh.getHealthTree().resetCurrentNode();
	   DecisionTree<String> node1 =(DecisionTree<String>) hh.getHealthTree();
		  node1.resetCurrentNode();
		  
		   node1.moveToNo();
		   node1.moveToYes();
		   assertEquals("CovidHealthBuild learn/updateTree method is not working properly", "unfit", node1.getCurrentNode().getData());
	 
		
} 

@Test(expected = EmptyTreeException.class) 
public void CovidHealthBuild_Exception_01() {
	 CovidHealthBuilder hh = new CovidHealthBuilder("data3.txt");
	 hh.decide();
	 
}
private static ByteArrayOutputStream localOut, localErr;
private static ByteArrayInputStream localIn;
private static PrintStream sysOut, sysErr;
private static InputStream sysIn;
public static final String [] empty = {};

//@BeforeClass
public static void setUp() {
  sysOut = System.out;
  sysErr = System.err;
  sysIn  = System.in;
}

//@AfterClass
public static void cleanUp() {
	  unsetCapture();
	  unsetInput();
}

public static void resetIO() {
	  setCapture();
	  setInput("");
}

// Before every test is run, reset the streams to capture
// stdout/stderr
//@Before
public static void setCapture() {
  localOut = new ByteArrayOutputStream();
  localErr = new ByteArrayOutputStream();
  System.setOut(null);
  System.setErr(null);
  System.setOut(new PrintStream(localOut));
  System.setErr(new PrintStream(localErr));
}

public static String getCapture() {
  return localOut.toString().replaceAll("\\r?\\n", "\n");
}

// After every test, restore stdout/stderr
//@After
public static void unsetCapture() {
  System.setOut(null);
  System.setOut(sysOut);
  System.setErr(null);
  System.setErr(sysErr);
}

public static void setInput(String input) {
  localIn = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
  System.setIn(localIn);
}

public static void unsetInput() {
  try {
    if (localIn != null) localIn.close();
  }
  catch (Exception e) {}
  System.setIn(null);
  System.setIn(sysIn);
}
}



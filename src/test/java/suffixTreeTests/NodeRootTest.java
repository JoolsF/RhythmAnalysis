package suffixTreeTests;

import static org.junit.Assert.*;
import model.InnerNode;
import model.Node;
import model.NodeLeaf;
import model.NodeRoot;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NodeRootTest {
	
	private NodeRoot root1;
	private NodeLeaf child1;
	
	@Before
	public void setUp() throws Exception {
		
		child1 = new NodeLeaf("aa", 0, root1);
		root1 = new NodeRoot();
		//root1.addChild(child1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSwapNode() {
		NodeLeaf child2 = new NodeLeaf("x", 0, root1);
		root1.swapNode(child1, child2);
		for(InnerNode next: root1.children){
			System.out.println(next.getString());
		}
	}

}

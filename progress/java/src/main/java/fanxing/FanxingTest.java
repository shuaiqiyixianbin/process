package fanxing;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型及其实现类调用方法测试
 */
public class FanxingTest {

	public static void main(String[] args) {
		List<FanxingInterface> fanxingInterfaceList = new ArrayList<>();
		FanxingInterface fanxingChilldOne = new FanxingChilldOneImpl();
		FanxingInterface fanxingChilldTwo = new FanxingChilldTwoImpl();
		FanxingInterface fanxingChilldTwoOld = new FanxingChilldTwoImpl();
		fanxingInterfaceList.add(fanxingChilldOne);
		fanxingInterfaceList.add(fanxingChilldTwo);
		fanxingInterfaceList.add(fanxingChilldTwoOld);
		fanxingInterfaceList.forEach(fanxingInterface -> fanxingInterface.process());
	}
}

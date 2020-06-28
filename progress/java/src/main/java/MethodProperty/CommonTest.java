package MethodProperty;

public class CommonTest {
	private String name;

	@Parameter(key = "price = 20")
	private String price;

	private int count;

	public String getName() {
		return name;
	}

	@Parameter(key="good name")
	public void setName(String name) {
		this.name = name;
	}


	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}

package me.designpattern.code.composit;

import java.util.ArrayList;
import java.util.List;

public class Bag implements Component {
	private final List<Component> Components;

	public Bag() {
		this.Components = new ArrayList<>();
	}

	public void add(Item item) {
		this.Components.add(item);
	}

	public List<Component> getComponents() {
		return Components;
	}

	@Override
	public int getPrice() {
		return this.Components.stream().mapToInt(Component::getPrice).sum();
	}
}

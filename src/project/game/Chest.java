package project.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Chest extends Item implements Iterable<Item>{
	private List<Item> items = new ArrayList<Item>();

	public Chest(Item item){
		items.add(item);
	}

	@Override
	public String getFilename() {
		return "assets\\Chest.png";
	}

	/**
	 * @return the item at the given index
	 */
	public Item getItem(int index) {
		return items.get(index);
	}

	/**
	 * @param item the item to add
	 */
	public void addItem(Item item) {
		this.items.add(item);
	}

	/**
	 * Removes the parameter item
	 * @return true if the item was removed
	 */
	public boolean removeItem(Item item){
		return items.remove(item);
	}

	/**
	 * Removes the item at the parameter index
	 * @return the item that was at the parameter index
	 */
	public Item removeItem(int index){
		return items.remove(index);
	}

	@Override
	public Iterator<Item> iterator() {
		return items.iterator();
	}
}

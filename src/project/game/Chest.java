package project.game;

public class Chest extends Item{
	private Item item;

	@Override
	public String getFilename() {
		return "assets\\Chest.png";
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	public Item removeItem(){
		Item result = item;
		item = null;
		return result;
	}
}

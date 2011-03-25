package com.caimeng.uilibray.container;

import java.util.Vector;

import com.caimeng.uilibray.common.AbstructButton;



public class ButtonGroup {

	private Vector group = new Vector();

	public void add(AbstructButton button) {
		button.setGroup(this);
		group.addElement(button);
	}

	public void remove(AbstructButton button) {
		button.setGroup(null);
		group.removeElement(button);
	}

	public void clearSelection() {
		int length = group.size();
		for (int i = 0; i < length; i++) {
			buttonTemp = (AbstructButton) group.elementAt(i);
			if (buttonTemp.isSelected()) {
				buttonTemp.setSelected(false);
				break;
			}
		}
	}

	private AbstructButton buttonTemp = null;

	public void setSelected(AbstructButton button) {
		int length = group.size();

		for (int i = 0; i < length; i++) {
			buttonTemp = (AbstructButton) group.elementAt(i);
			if (buttonTemp.equals(button)) {
				if (!buttonTemp.isSelected()) {
					buttonTemp.setSelected(true);
				}
			} else {
				buttonTemp.setSelected(false);
			}
		}
	}

	public boolean isSelected(AbstructButton button) {
		int length = group.size();
		boolean selected = false;
		for (int i = 0; i < length; i++) {
			buttonTemp = (AbstructButton) group.elementAt(i);
			if (buttonTemp.equals(button)) {
				if (buttonTemp.isSelected()) {
					selected = true;
				}
				break;
			}
		}
		return selected;
	}

	public int getButtonCount() {
		return group.size();
	}

}

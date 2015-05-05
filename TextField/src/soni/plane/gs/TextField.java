package soni.plane.gs;

import soni.plane.api.implement.Window;
import soni.plane.api.tools.Keyboard;

/** TODO: make support for special characters
  * TODO: ensure works on all systems
  * TODO: make less hacky **/

public class TextField {
	/* text inputted here currently */
	private String text;
	/* if TextField is focused at the moment */
	private boolean focused = false;

	/* create this Textfield */
	public TextField(String text, Window parent){
		this.text = text;
		Keyboard.addListener(new TextFieldKeyListener(this), parent);
	}

	/* get stored text */
	public String getText(){
		return text;
	}

	/* set text */
	public TextField setText(String text){
		this.text = text;
		return this;
	}

	/* set whether app is focused or not */
	public void setFocus(boolean f){
		focused = f;
	}

	/* return if focused or now */
	public boolean hasFocus(){
		return focused;
	}
}

package soni.plane.gs;

import soni.plane.api.tools.KeyListener;

public class TextFieldKeyListener extends KeyListener {
	/* accessor context of Textfield in use */
	private final TextField ctx;

	public TextFieldKeyListener(TextField context) {
		ctx = context;
	}

	@Override
	public void keyDown(int i) {

	}

	@Override
	public void keyUp(int i) {

	}

	/* TODO: improve this shit */
	@Override
	public void keyTyped(char c) {
		if(ctx.hasFocus()) {
			if (c == '\b') {
				if (ctx.getText().length() >= 1) {
					ctx.setText(ctx.getText().substring(0, ctx.getText().length() - 1));
				}
				return;
			}

			ctx.setText(ctx.getText() + c);
		}
	}

	@Override
	public void touchUp(int i, int i1, int i2) {

	}

	@Override
	public void touchDown(int i, int i1, int i2) {

	}

	@Override
	public void touchDragged(int i, int i1) {}

	@Override
	public void mouseMoved(int i, int i1) {}

	@Override
	public void scrollTo(float v) {}
}

/**
 * Multi-functional and the most crucial token in the game. It is a composition of mirror, stuckable, and target.
 * Mirror side reflects in the same way with BlueMirror. If Target is hit, beam is stucked ath there and lights the
 * Target. Aim of this game is hitting "labeled" Targets. If laser hits the empty edge, it becomes stucked, as well.
 */

package tokens;

import core.Direction;
import interfaces.Reflectable;
import interfaces.Stuckable;
import interfaces.Targetable;

public class PurpleTarget extends Token implements Reflectable, Stuckable, Targetable {
	
	
	
	@Override
	public Direction action() {
		// TODO Auto-generated method stub
		return super.action();
	}
	
}

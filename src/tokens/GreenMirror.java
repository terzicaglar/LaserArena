/**
 * Just like BlueMirror, reflects incoming laser by 90 degrees and mirrors are placed in both directions of this token. In addition,
 * it creates a new laser beam that transparently passes through the mirror.
 */

package tokens;

import interfaces.BeamCreator;
import interfaces.Transparent;
import interfaces.Reflectable;

public class GreenMirror extends Token implements Reflectable, BeamCreator, Transparent {
}

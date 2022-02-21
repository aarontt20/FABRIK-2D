package fabrik;

import java.util.ArrayList;
import java.util.HashMap;

public class FABRIK {
	static void solve(Bone2D relativeRoot, Bone2D endEffector, Vector2 target) {
		ArrayList<Bone2D> bones = new ArrayList<Bone2D>();

		Bone2D curr = endEffector;
		while (curr != null) {
			bones.add(0, curr);

			curr = curr.getParent();
		}

		HashMap<Bone2D, Matrix3x3> local = new HashMap<Bone2D, Matrix3x3>();
		ArrayList<Vector2> startingPoints = new ArrayList<Vector2>();
		ArrayList<Float> angles = new ArrayList<Float>();
		Vector2 start = null;
		boolean inField = false;

		local.put(bones.get(0), bones.get(0).getTransform());

		if (bones.get(0) == relativeRoot) {
			startingPoints.add(bones.get(0).getPosition());
			start = bones.get(0).getPosition();
			inField = true;
		}

		for (int i = 1; i < bones.size(); i++) {
			if (bones.get(i).isConnected())
				bones.get(i).setPosition(new Vector2(bones.get(i).getParent().getLength(), 0));

			Matrix3x3 temp = bones.get(i).getTransform();
			temp.preApply(local.get(bones.get(i).getParent()));

			local.put(bones.get(i), temp);

			if (bones.get(i) == relativeRoot) {
				start = new Vector2(temp.m02, temp.m12);
				inField = true;
			}

			if (bones.get(i).getParent() != null) {
				float a = Vector2.angleBetween(bones.get(i).getPosition(), new Vector2(1, 0));
				if (bones.get(i).getPosition().y < 0)
					a = (float)(Math.PI + Math.PI - a);
				angles.add(a);
			}

			if (inField)
				startingPoints.add(new Vector2(temp.m02, temp.m12));
		}

		angles.add(0.0f);

		ArrayList<Vector2> backPoints = new ArrayList<Vector2>();
		ArrayList<Vector2> forwardPoints = new ArrayList<Vector2>();
		ArrayList<Float> lengths = new ArrayList<Float>();
		
		//Backward propagation

		backPoints.add(target);

		curr = endEffector;

		lengths.add(0, curr.getLength());

		for (int i = startingPoints.size() - 1; i >= 0; i--) {
			backPoints.add(0, backPoints.get(0).copy()
					.add(startingPoints.get(i).copy().sub(backPoints.get(0)).normalize().mult(lengths.get(0))));

			if (curr.getParent() != null)
				lengths.add(0, curr.getPosition().mag());
			else
				lengths.add(0, 0.0f);

			curr = curr.getParent();
		}
		
		//Forward propagation

		forwardPoints.add(start);
		
		for (int i = 1; i < backPoints.size(); i++) {
			Vector2 offset = backPoints.get(i).copy().sub(forwardPoints.get(i - 1)).normalize().mult(lengths.get(i));
			forwardPoints.add(forwardPoints.get(i - 1).copy().add(offset));
		}

		float theta = 0;
		boolean b = false;
		int f = 0;

		for (int i = 0; i < bones.size(); i++) {
			if (bones.get(i) == relativeRoot) {
				b = true;
			}

			if (b) {
				Vector2 dir = forwardPoints.get(f + 1).copy().sub(forwardPoints.get(f)).normalize();
				bones.get(i).setDirection(dir.rotate(-theta - angles.get(i)));
				f++;
			}

			theta += bones.get(i).getAngle();

			if (bones.get(i) == endEffector)
				break;
		}
	}
}

package fabrik;

import java.util.HashMap;


// Requirement: Parents of bones must appear in the bones array before the child
class Rig2D {
	public Bone2D[] bones;

	public Rig2D(Bone2D[] bones) {
		this.bones = bones;
	}

	public HashMap<Bone2D, Matrix3x3> calcBoneWorldTransforms() {
		HashMap<Bone2D, Matrix3x3> world = new HashMap<Bone2D, Matrix3x3>();

		world.put(bones[0], bones[0].getTransform());

		for (int i = 1; i < bones.length; i++) {
			Matrix3x3 temp = bones[i].getTransform();
			temp.preApply(world.get(bones[i].getParent()));

			world.put(bones[i], temp);
		}

		return world;
	}
}
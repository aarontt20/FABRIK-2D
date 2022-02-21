# FABRIK-2D
A Java implementation for the iterative solution to solving 2D inverse kinematics called FABRIK.

This program uses instances of Bone2D as the actuators for an IK Rig2D. 

Each Bone2D has a parent Bone2D, a position relative to a parent, a length, an associated angle, and 
constraints for governing the movement capabilities. The setup of bones is left to the user with the 
knowledge that the root bone will have a null parent, and the position of the root determines the world 
coordinates of the rig. Any child bones will have positions and angles relative to their parent bone. 
Bones can also be specified to be “connected” meaning their positions are automatically placed at the 
tail of their parent bone.

A Rig2D is simply a container that holds an array of Bone2Ds that can calculate the world transformation 
of an entire rig from a root bone. The root bone must be the first bone in the container array, and all 
parent bones must appear before their children in the array.

The static solve method of the FABRIK class will perform one iteration of the FABRIK algorithm, actuating
and updating the bones from a specified relative root to a specified end actuator with a target world 
position. A subsequent call to the Rig2D calcBoneWorldTransforms will calculate the world transformation 
for each bone given the current state of the rig and return it in a HashMap.

I hope somebody can find my implementation of a 2D FABRIK solver useful! Please let me know if there are 
any errors or bugs!

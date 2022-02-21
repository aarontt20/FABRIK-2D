package fabrik;

public class Bone2D {
	private Bone2D parent;
	private Vector2 pos;
	private float len;
	private float angle;
	private boolean connected;
	private Constraint constraint = new Constraint();
	
	
	Bone2D(Bone2D parent_, Vector2 pos_, float len_, float angle_) {
		this.parent = parent_;
		this.pos = pos_;
		this.len = len_;
		this.angle = angle_;
	}
	Bone2D(Bone2D parent_, Vector2 pos_, float len_, float angle_, boolean connected_) {
		this.parent = parent_;
		this.pos = pos_;
		this.len = len_;
		this.angle = angle_;
		this.connected = connected_;
	}
	
	public Bone2D getParent()			{	return parent;		}
	public Vector2 getPosition()		{	return pos;			}
	public float getLength()			{	return len;			}
	public float getAngle()				{	return angle;		}
	public boolean isConnected()		{	return connected;	}
	public Constraint getConstraint()	{	return constraint;	}
	
	public void setParent(Bone2D parent_)			{	this.parent = parent_;			}
	public void setPosition(Vector2 pos_)			{	this.pos = pos_;				}
	public void setLength(float len_)				{	this.len = len_;				}
	public void setConnected(boolean connected_)	{	this.connected = connected_;	}
	
	void setAngle(float angle_) {
		if (constraint.isEnabled()) {
			if (!constraint.isLocked()) {
				this.angle = angle_;

				constraint.constrain();
			}
		} 
		else
			this.angle = angle_;
	}
	
	public Vector2 getDirection() {
		return new Vector2(1, 0).rotate(angle);
	}
	
	void setDirection(Vector2 dir) {
		if (constraint.isEnabled()) {
			if (!constraint.isLocked()) {
				this.angle += Vector2.signedAngleBetween(getDirection(), dir);

				constraint.constrain();
			}
		} else
			this.angle += Vector2.signedAngleBetween(new Vector2(1, 0).rotate(angle), dir);
	}
	
	Matrix3x3 getTransform() {
		float x = pos.x;
		float y = pos.y;

		if (connected) {
			x = parent.getLength();
			y = 0;
		}

		float cos = (float)Math.cos(angle);
		float sin = (float)Math.sin(angle);

		return new Matrix3x3(cos, -sin, x, sin, cos, y, 0, 0 ,1);
	}

	void rotate(float theta) {
		if (constraint.isEnabled()) {
			if (!constraint.isLocked()) {
				this.angle += theta;

				constraint.constrain();
			}
		} else
			this.angle += theta;
	}
	
	public class Constraint {
		private float minAngle, maxAngle;
		private boolean locked;
		private boolean active;
		
		public Constraint() {
			this.minAngle = Float.NEGATIVE_INFINITY;
			this.minAngle = Float.POSITIVE_INFINITY;
		}
		public Constraint(float minAngle_, float maxAngle_) {
			this.minAngle = minAngle_;
			this.minAngle = maxAngle_;
		}
		
		public float getMinAngle()	{	return minAngle;	}
		public float getMaxAngle()	{	return maxAngle;	}
		public boolean isLocked()	{	return locked;		}
		public boolean isEnabled()	{	return active;		}
		
		public void setMinAngle(float minAngle_)	{	this.minAngle = minAngle_;	}
		public void setMaxAngle(float maxAngle_)	{	this.maxAngle = maxAngle_;	}
		public void setLock(boolean locked_)		{	this.locked = locked_;		}
		
		public void setActive(boolean active_) {
			this.active = active_;
			
			constrain();
		}
		
		public void lock()		{	this.locked = true;		}
		public void unlock()	{	this.locked = false;	}
		public void disable()	{	this.active = false;	}
		
		void enable()	{
			this.active = true;
			
			constrain();
		}
		
		private void constrain() {
			if(angle < constraint.getMinAngle())
				angle = constraint.getMinAngle();
			if(angle > constraint.getMaxAngle())
				angle = constraint.getMaxAngle();
		}
	}
}

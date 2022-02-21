package fabrik;

public class Vector2 {
	public float x;
	public float y;
	
	public Vector2(float x_, float y_) {
		this.x = x_;
		this.y = y_;
	}
	
	public Vector2 rotate(float angle) {
		float rx = (float)((this.x * Math.cos(angle)) - (this.y * Math.sin(angle)));
	    float ry = (float)((this.x * Math.sin(angle)) + (this.y * Math.cos(angle)));
	    x = rx;
	    y = ry;
	    
	    return this;
	}

	public Vector2 copy() {
		return new Vector2(x, y);
	}
	
	public Vector2 add(Vector2 v) {
		x += v.x;
		y += v.y;
		return this;
	}
	
	public Vector2 sub(Vector2 v) {
		x -= v.x;
		y -= v.y;
		return this;
	}
	
	public Vector2 mult(float scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}
	
	public float mag() {
		return (float)Math.sqrt(x*x + y*y);
	}
	
	public Vector2 normalize() {
		float mag = mag();
		
		x /= mag;
		y /= mag;
		
		return this;
	}
	
	public static float angleBetween(Vector2 v1, Vector2 v2) {
		float dot = v1.x*v2.x + v1.y+v2.y;
		return (float)Math.acos(dot/(v1.mag()*v2.mag()));
	}
	
	public static float signedAngleBetween(Vector2 v1, Vector2 v2) {
		return (float)Math.atan2(v1.x * v2.y - v1.y * v2.x, v1.x * v2.x + v1.y * v2.y);
	}
}

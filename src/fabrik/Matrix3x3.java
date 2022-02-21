package fabrik;

public class Matrix3x3 {
	public float m00, m01, m02;
	public float m10, m11, m12;
	public float m20, m21, m22;
	
	public Matrix3x3() {
		m00 = 1.0f;
		m01 = 0.0f;
		m02 = 0.0f;
		m10 = 0.0f;
		m11 = 1.0f;
		m12 = 0.0f;
		m20 = 0.0f;
		m21 = 0.0f;
		m22 = 1.0f;
	}
	
	public Matrix3x3(float m00_, float m01_, float m02_, float m10_, float m11_, float m12_,  float m20_, float m21_, float m22_) {
		m00 = m00_;
		m01 = m01_;
		m02 = m02_;
		m10 = m10_;
		m11 = m11_;
		m12 = m12_;
		m20 = m20_;
		m21 = m21_;
		m22 = m22_;
	}
	
	public Matrix3x3 preApply(Matrix3x3 mat) {
		float n00 = mat.m00 * m00 + mat.m01 * m10 + mat.m02 * m20;
		float n01 = mat.m00 * m01 + mat.m01 * m11 + mat.m02 * m21;
		float n02 = mat.m00 * m02 + mat.m01 * m12 + mat.m02 * m22;
		float n10 = mat.m10 * m00 + mat.m11 * m10 + mat.m12 * m20;
		float n11 = mat.m10 * m01 + mat.m11 * m11 + mat.m12 * m21;
		float n12 = mat.m10 * m02 + mat.m11 * m12 + mat.m12 * m22;
		float n20 = mat.m20 * m00 + mat.m21 * m10 + mat.m22 * m20;
		float n21 = mat.m20 * m01 + mat.m21 * m11 + mat.m22 * m21;
		float n22 = mat.m20 * m02 + mat.m21 * m12 + mat.m22 * m22;
		
		m00 = n00;
		m01 = n01;
		m02 = n02;
		m10 = n10;
		m11 = n11;
		m12 = n12;
		m20 = n20;
		m21 = n21;
		m22 = n22;
		
		return this;
	}
}

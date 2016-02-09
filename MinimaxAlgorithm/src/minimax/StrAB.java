package minimax;

public class StrAB {
	 public int AlphaBeta;
	 public int AttrInd;
	 public int AttrVal;

	public StrAB(int AlphaBeta, int AttrInd, int AttrVal){
		 super();
		 this.AlphaBeta = AlphaBeta;
		 this.AttrInd = AttrInd;
		 this.AttrVal = AttrVal;
	}
	public int getAlphaBeta() {
		return AlphaBeta;
	}

	public void setAlphaBeta(int alphaBeta) {
		AlphaBeta = alphaBeta;
	}

	public int getAttrInd() {
		return AttrInd;
	}

	public void setAttrInd(int attrInd) {
		AttrInd = attrInd;
	}

	public int getAttrVal() {
		return AttrVal;
	}

	public void setAttrVal(int attrVal) {
		AttrVal = attrVal;
	};	 
	
}

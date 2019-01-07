package com.hws.model;

public class ChineseMedicine {
	/**
	 * @author hws description:�г�ҩʵ����
	 */

	private int mId;// �г�ҩid,����
	private String mcName;// �г�ҩ����
	private String mpName;// �г�ҩ����ƴ��
	private String mFormulation;// ����
	private String mIngredient;// �ɷ�
	private String mTraits;// ��״
	private String mIndications;// ��Ӧ֢
	private String mSpecification;// ���
	private String mDosage;// �÷�����
	private String mNegativereactions;// ������Ӧ
	private String mBan;// ����
	private String mNotice;// ע������
	private String mInteraction;// �໥����
	private String mStorage;// ����
	private String mValidity;// ��Ч����
	private String mPackage;// ��װ
	private String mPicture;// ͼƬ��ַ
	private int mTypeId;// ����id
	private String tname;//ҩƷ���
	private String bookname;//ҩƷ��׼��Դ����
	private	String approvalnumber;//��׼�ĺ�
	private String manufacturer;//���ɳ���
	
	
	//standardsource��
	private StandardSource sSource;
	private CMType cmType;
	private Instructions instuctions;
	

	
	public Instructions getInstuctions() {
		return instuctions;
	}

	public void setInstuctions(Instructions instuctions) {
		this.instuctions = instuctions;
	}

	public CMType getCmType() {
		return cmType;
	}

	public void setCmType(CMType cmType) {
		this.cmType = cmType;
	}

	public String getApprovalnumber() {
		return approvalnumber;
	}

	public void setApprovalnumber(String approvalnumber) {
		this.approvalnumber = approvalnumber;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public StandardSource getsSource() {
		return sSource;
	}

	public void setsSource(StandardSource sSource) {
		this.sSource = sSource;
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public String getMcName() {
		return mcName;
	}

	public void setMcName(String mcName) {
		this.mcName = mcName;
	}

	public String getMpName() {
		return mpName;
	}

	public void setMpName(String mpName) {
		this.mpName = mpName;
	}

	public String getmFormulation() {
		return mFormulation;
	}

	public void setmFormulation(String mFormulation) {
		this.mFormulation = mFormulation;
	}

	public String getmIngredient() {
		return mIngredient;
	}

	public void setmIngredient(String mIngredient) {
		this.mIngredient = mIngredient;
	}

	public String getmTraits() {
		return mTraits;
	}

	public void setmTraits(String mTraits) {
		this.mTraits = mTraits;
	}

	public String getmIndications() {
		return mIndications;
	}

	public void setmIndications(String mIndications) {
		this.mIndications = mIndications;
	}

	public String getmSpecification() {
		return mSpecification;
	}

	public void setmSpecification(String mSpecification) {
		this.mSpecification = mSpecification;
	}

	public String getmDosage() {
		return mDosage;
	}

	public void setmDosage(String mDosage) {
		this.mDosage = mDosage;
	}

	public String getmNegativereactions() {
		return mNegativereactions;
	}

	public void setmNegativereactions(String mNegativereactions) {
		this.mNegativereactions = mNegativereactions;
	}

	public String getmBan() {
		return mBan;
	}

	public void setmBan(String mBan) {
		this.mBan = mBan;
	}

	public String getmNotice() {
		return mNotice;
	}

	public void setmNotice(String mNotice) {
		this.mNotice = mNotice;
	}

	public String getmInteraction() {
		return mInteraction;
	}

	public void setmInteraction(String mInteraction) {
		this.mInteraction = mInteraction;
	}

	public String getmStorage() {
		return mStorage;
	}

	public void setmStorage(String mStorage) {
		this.mStorage = mStorage;
	}

	public String getmValidity() {
		return mValidity;
	}

	public void setmValidity(String mValidity) {
		this.mValidity = mValidity;
	}

	public String getmPackage() {
		return mPackage;
	}

	public void setmPackage(String mPackage) {
		this.mPackage = mPackage;
	}

	public String getmPicture() {
		return mPicture;
	}

	public void setmPicture(String mPicture) {
		this.mPicture = mPicture;
	}

	public int getmTypeId() {
		return mTypeId;
	}

	public void setmTypeId(int mTypeId) {
		this.mTypeId = mTypeId;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	@Override
	public String toString() {
		return "ChineseMedicine [mId=" + mId + ", mcName=" + mcName + ", mpName=" + mpName + ", mFormulation="
				+ mFormulation + ", mIngredient=" + mIngredient + ", mTraits=" + mTraits + ", mIndications="
				+ mIndications + ", mSpecification=" + mSpecification + ", mDosage=" + mDosage + ", mNegativereactions="
				+ mNegativereactions + ", mBan=" + mBan + ", mNotice=" + mNotice + ", mInteraction=" + mInteraction
				+ ", mStorage=" + mStorage + ", mValidity=" + mValidity + ", mPackage=" + mPackage + ", mPicture="
				+ mPicture + ", mTypeId=" + mTypeId + ", tname=" + tname + ", bookname=" + bookname
				+ ", approvalnumber=" + approvalnumber + ", manufacturer=" + manufacturer + ", sSource=" + sSource
				+ ", cmType=" + cmType + ", instuctions=" + instuctions + "]";
	}

}

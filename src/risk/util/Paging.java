package risk.util;

public class Paging {
	
	private int pageNo;						//현제페이지번호
	private int pageGroupStartNo;			//페이지 그룹 시작번호
	private int pageGroupEndNo;				//페이지 그룹 종료번호
	private int dataSize;					//데이터사이즈,
	private int totalDataCount;				//전체 데이터 카운트
	private int totalPage;					//전체 페이지수
	private int pageGroupSize;				//페이지 그룹번호
	private int pageStartRow;				//페이지 시작 Row번호
	private int pageEndRow;					//페이지 종료 Row번호
	private int pageGroup;					//페이지 그룹번호
	private int pageGroupDataStartRow;		//페이지그룹의 시작 Row
	private int pageGroupDataEndRow;		//페이지그룹의 종료 Row
	private int pageNextGroupDataFirstRow;	//다음페이지그룹의 첫번째 Row
	private boolean isNextPageGroup;		//다음페이지 그룹 유무
	private boolean isPrevPageGroup;		//이전페이지 그룹 유무
	private int nextPageNo;					//다음페이지 번호,
	private int prevPageNo;					//이전페이지 번호,
	

	public Paging(Object pageNo,Object totalDataCount) {
		this(pageNo,totalDataCount,10);
	}
	
	public Paging(Object pageNo,Object totalDataCount,Object dataSize) {
		this(pageNo,totalDataCount,dataSize,10);
	}
	
	public Paging(Object pageNo,Object totalDataCount,Object dataSize, Object pageGroupSize) {
		this.pageNo = Integer.parseInt(pageNo.toString());
		this.totalDataCount = Integer.parseInt(totalDataCount.toString());
		this.dataSize = Integer.parseInt(dataSize.toString());
		this.pageGroupSize = Integer.parseInt(pageGroupSize.toString());
		this.createPaging();
	}
	
	

	private void createPaging() {
		totalPage = (int) Math.ceil(totalDataCount/(double)dataSize);																																			
		pageStartRow = (pageNo - 1) * dataSize + 1;																																
		pageEndRow = pageNo * dataSize;																																			
		pageGroup = (pageNo - 1) / pageGroupSize + 1;																																			
		pageGroupStartNo = (pageGroup - 1) * pageGroupSize + 1;																																			
		pageGroupEndNo = pageGroup * pageGroupSize;																																			
		if(pageGroupEndNo > totalPage) pageGroupEndNo=totalPage;																																		
		pageGroupDataStartRow = (pageGroupStartNo - 1) * dataSize;																																			
		pageGroupDataEndRow = pageGroupEndNo * dataSize;																																			
		if(pageGroupDataEndRow > totalDataCount) pageGroupDataEndRow = totalDataCount;																																		
		pageNextGroupDataFirstRow = pageGroupEndNo * dataSize;
		isPrevPageGroup = pageGroup > 1 ? true : false;
		isNextPageGroup = (pageGroup * pageGroupSize +1) <= totalPage ? true : false;
		
		prevPageNo = isPrevPageGroup ? pageGroupStartNo-1: 1;
		nextPageNo = isNextPageGroup ? pageGroupEndNo+1: totalPage;			
		
		
	}
	
	/**
	 * @return	다음페이지 페이지번호
	 */
	public int getNextPageNo() {
		return nextPageNo;
	}

	/**
	 * @return	이전페이지 페이지번호
	 */
	public int getPrevPageNo() {
		return prevPageNo;
	}


	/**
	 * @return	현제 페이지번호
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * @return	페이징 시작번호
	 */
	public int getPageGroupStartNo() {
		return pageGroupStartNo;
	}

	/**
	 * @return	페이징 종료번호
	 */
	public int getPageGroupEndNo() {
		return pageGroupEndNo;
	}


	/**
	 * @return	전체 데이터 수
	 */
	public int getTotalDataCount() {
		return totalDataCount;
	}

	/**
	 * @return	전체 페이지수
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * @return	다음페이지 그룹 유무
	 */
	public boolean isNextPageGroup() {
		return isNextPageGroup;
	}

	/**
	 * @return	이전페이지 그룹 유무
	 */
	public boolean isPrevPageGroup() {
		return isPrevPageGroup;
	}



	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n").append("(pageNo)현재 페이지 번호 			: "+pageNo);
		sb.append("\n").append("(totalPage)전체 페이지 수 			: "+totalPage);
		sb.append("\n").append("(totalDataCount)전체 데이터 카운트		: "+totalDataCount);
		sb.append("\n").append("(pageGroupStartNo)페이지 시작 번호 		: "+pageGroupStartNo);
		sb.append("\n").append("(pageGroupEndNo)페이지 종료 번호 		: "+pageGroupEndNo);
		sb.append("\n").append("(isPrevPageGroup)이전 페이지 그룹유무		: "+isPrevPageGroup);
		sb.append("\n").append("(isNextPageGroup)다음 페이지 그룹유무		: "+isNextPageGroup);
		sb.append("\n").append("(prevPageNo)이전 페이지 번호 			: "+prevPageNo);
		sb.append("\n").append("(nextPageNo)다음 페이지 번호 			: "+nextPageNo);
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Paging paging2 = new Paging("10", "63", "5", "5");
		System.out.println(paging2);
	}

}

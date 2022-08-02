
   //새로고침 관련 자바스크립트 By  윤석준
    /****************************************************/
    /* 새로고침 이후 지난 시간을 표출하기 위한 스크립트 */
    /****************************************************/
    var refreshinterval=0;

    var startRefreshTime;
    var nowtime;
    var reloadseconds=0;
    var secondssinceloaded=0;


    var currentsec=0;
    var currentmin=0;
    var currentmil=0;
    var refreshing=false;
    var refreshTimer;

    
    function starttime() {
       startRefreshTime=new Date();
       startRefreshTime=startRefreshTime.getTime();
       countdown();
    }
	
    function countdown() {
        nowtime= new Date();
        nowtime=nowtime.getTime();
        secondssinceloaded= (nowtime - startRefreshTime) /1000;
        reloadseconds = Math.round(refreshinterval - secondssinceloaded);

        if (refreshing) {

            if (refreshinterval >= secondssinceloaded) {
                refreshTimer=setTimeout("countdown()",1000);
            } else {
                clearTimeout(refreshTimer);
                if( "Y" == $("#timer").val() ){
                	doSubmit();
            	}

            }

            currentsec+=1;
            if (currentsec==60){
                currentsec=0;
                currentmin+=1;
            }
            Strsec=""+currentsec;
            Strmin=""+currentmin;
            Strmil=""+currentmil;

            if (Strsec.length!=2){
                Strsec="0"+currentsec;
            }
            if (Strmin.length!=2){
                Strmin="0"+currentmin;
            }
            $("#watch").empty().html("<strong style='font-size:10px;'>" + Strmin + ":" + Strsec + "초전 정보</strong>");
        }
    }

    function setTimer( time ) {
        if ( time == "" || time == "0" ) {
            refreshing = false;
        } else {
            refreshing = true;
        }
        if ( refreshing == true ) {
        	//$("#timer").val("Y");
        	//$("#interval").val(time);

            clearTimeout(refreshTimer);
            currentsec=0;
            currentmin=0;
            currentmil=0;
            Strsec="00";
            Strmin="00";
            Strmil="00";


            refreshinterval = time * 60 ;
            // $("#timer").val("Y");
            //$("#interval").val(time);
            starttime();

        } else {

        	//$("#timer").val("N");
        	//$("#interval").val(time);

            clearTimeout(refreshTimer);

            refreshing=false;
            currentsec=0;
            currentmin=0;
            currentmil=0;
            Strsec="00";
            Strmin="00";
            Strmil="00";
            $("#watch").empty().html("<strong style='font-size:10px;'>새로고침 중지중</strong>") ;
        }
    }
    
	function updateRefreshTime(time, m_no){
		$.ajax({ 
			url : "../common/setTimer.jsp"
			,type : "POST"
			,timeout : 1800000
			,dataType : "json"
			,data : {
				refresh_time:time
				,m_no:m_no
				,mode:'update'
			},success : function(data){
				if(doSubmit());
			}
		
		});
	}
	
	function getRefreshTime(){
		$.ajax({ 
			url : "../common/setTimer.jsp"
			,type : "POST"
			,timeout : 1800000
			,dataType : "json"
			,data : {
				mode:'select'
			},success : function(data){
				if("0"==data){
					$("#timer").val("N");
					$("#interval").val(data);
				}else{
					$("#timer").val("Y");
					$("#interval").val(data);
					setTimer(data);
				}
			}
		
		});
	}

    /****************************************************/

/*!
 *
 * @author: GUNI, h2dlhs@realsn.com
 **/
 
 
(function ($) {

	var mapClass = function( $el, $options ) {

		
		var _map_geo_all_data = {};
		var _map_data;

		var mapLevel = 0;
		var mapCode = "000";

		var legendDatas = [ 
			{ min: 0, max: 0, fill: "#e3e3e3" },
			{ min: 0, max: 0, fill: "#d4d4d4" },
			{ min: 0, max: 0, fill: "#c3c3c3" },
			{ min: 0, max: 0, fill: "#b0b0b0" },
			{ min: 0, max: 0, fill: "#a1a1a1" },
			{ min: 0, max: 0, fill: "#b6d6c7" },
			{ min: 0, max: 0, fill: "#87c8a9" },
			{ min: 0, max: 0, fill: "#5aa883" },
			{ min: 0, max: 0, fill: "#418a68" },
			{ min: 0, max: 0, fill: "#327053" }
		];
		var locations = [
			// 전국
			{ id: "001",		label: "서울",			latitude: 37.557121,		longitude: 127.006893 },
			{ id: "002",		label: "부산",			latitude: 35.165296, 		longitude: 129.045486 },
			{ id: "003",		label: "대구",			latitude: 35.830191,		longitude: 128.565117 },
			{ id: "004",		label: "인천",			latitude: 37.456010, 		longitude: 126.657087 },
			{ id: "005",		label: "광주",			latitude: 35.161657, 		longitude: 126.834402 },
			{ id: "006",		label: "대전",			latitude: 36.341639, 		longitude: 127.389824 },
			{ id: "007",		label: "울산",			latitude: 35.547607,		longitude: 129.255379 },
			{ id: "008",		label: "세종",			latitude: 36.561964,		longitude: 127.258772 },
			{ id: "009",		label: "경기",			latitude: 37.421641,		longitude: 127.405787 },
			{ id: "010",		label: "강원",			latitude: 37.638616, 		longitude: 128.349044 },
			{ id: "011",		label: "충북",			latitude: 36.599166, 		longitude: 127.694758 },
			{ id: "012",		label: "충남",			latitude: 36.415447, 		longitude: 126.817493 },
			{ id: "013",		label: "전북",			latitude: 35.623832,		longitude: 127.141346 },
			{ id: "014",		label: "전남",			latitude: 34.769820, 		longitude: 126.983739 },
			{ id: "015",		label: "경북",			latitude: 36.299825, 		longitude: 128.693412 },
			{ id: "016",		label: "경남",			latitude: 35.265446, 		longitude: 128.214349 },
			{ id: "017",		label: "제주",			latitude: 33.957724, 		longitude: 126.534939 },
			// 서울시
			{ id: "018",		label: "종로구",			latitude: 37.584697,		longitude: 126.973797 },
			{ id: "019",		label: "중구",			latitude: 37.555423,		longitude: 126.996020 },
			{ id: "020",		label: "용산구",			latitude: 37.531499,		longitude: 126.980299 },
			{ id: "021",		label: "성동구",			latitude: 37.548762,		longitude: 127.041118 },
			{ id: "022",		label: "광진구",			latitude: 37.545728,		longitude: 127.086431 },
			{ id: "023",		label: "동대문구",		latitude: 37.576992,		longitude: 127.055423 },
			{ id: "024",		label: "중랑구",			latitude: 37.587674,		longitude: 127.092812 },
			{ id: "025",		label: "성북구",			latitude: 37.593768,		longitude: 127.017440 },
			{ id: "026",		label: "강북구",			latitude: 37.638413,		longitude: 127.011864 },
			{ id: "027",		label: "도봉구",			latitude: 37.665799,		longitude: 127.031894 },
			{ id: "028",		label: "노원구",			latitude: 37.648664,		longitude: 127.075207 },
			{ id: "029",		label: "은평구",			latitude: 37.618979,		longitude: 126.926981 },
			{ id: "030",		label: "서대문구",		latitude: 37.577910,		longitude: 126.938878 },
			{ id: "031",		label: "마포구",			latitude: 37.555473,		longitude: 126.908048 },
			{ id: "032",		label: "양천구",			latitude: 37.519835,		longitude: 126.855143 },
			{ id: "033",		label: "강서구",			latitude: 37.561727,		longitude: 126.822820 },
			{ id: "034",		label: "구로구",			latitude: 37.494395,		longitude: 126.856199 },
			{ id: "035",		label: "금천구",			latitude: 37.460563,		longitude: 126.900693 },
			{ id: "036",		label: "영등포구",		latitude: 37.518089,		longitude: 126.910255 },
			{ id: "037",		label: "동작구",			latitude: 37.499121,		longitude: 126.951507 },
			{ id: "038",		label: "관악구",			latitude: 37.462374,		longitude: 126.945586 },
			{ id: "039",		label: "서초구",			latitude: 37.470675,		longitude: 127.031516 },
			{ id: "040",		label: "강남구",			latitude: 37.488857,		longitude: 127.063151 },
			{ id: "041",		label: "송파구",			latitude: 37.504960,		longitude: 127.116251 },
			{ id: "042",		label: "강동구",			latitude: 37.545227,		longitude: 127.146709 },
			// 부산시
			{ id: "043",		label: "중구",			latitude: 35.105345,		longitude: 129.032402 },
			{ id: "044",		label: "서구",			latitude: 35.080348,		longitude: 129.019082 },
			{ id: "045",		label: "동구",			latitude: 35.126586,		longitude: 129.046870 },
			{ id: "046",		label: "영도구",			latitude: 35.071089,		longitude: 129.067915 },
			{ id: "047",		label: "부산진구",		latitude: 35.160610,		longitude: 129.043267 },
			{ id: "048",		label: "동래구",			latitude: 35.206272,		longitude: 129.079023 },
			{ id: "049",		label: "남구",			latitude: 35.115916,		longitude: 129.093683 },
			{ id: "050",		label: "북구",			latitude: 35.224758,		longitude: 129.024165 },
			{ id: "051",		label: "해운대구",		latitude: 35.181128,		longitude: 129.155654 },
			{ id: "052",		label: "사하구",			latitude: 35.078427,		longitude: 128.980592 },
			{ id: "053",		label: "금정구",			latitude: 35.250977,		longitude: 129.091594 },
			{ id: "054",		label: "강서구",			latitude: 35.135386,		longitude: 128.895824 },
			{ id: "055",		label: "연제구",			latitude: 35.176779,		longitude: 129.083165 },
			{ id: "056",		label: "수영구",			latitude: 35.153189,		longitude: 129.112674 },
			{ id: "057",		label: "사상구",			latitude: 35.153733,		longitude: 128.988007 },
			{ id: "058",		label: "기장군",			latitude: 35.288448,		longitude: 129.195795 },
			// 대구시
			{ id: "059",		label: "중구",			latitude: 35.867049,		longitude: 128.593718 },
			{ id: "060",		label: "동구",			latitude: 35.934894,		longitude: 128.685875 },
			{ id: "061",		label: "서구",			latitude: 35.875124,		longitude: 128.549517 },
			{ id: "062",		label: "남구",			latitude: 35.835173,		longitude: 128.585447 },
			{ id: "063",		label: "북구",			latitude: 35.928840,		longitude: 128.577192 },
			{ id: "064",		label: "수성구",			latitude: 35.833846,		longitude: 128.661085 },
			{ id: "065",		label: "달서구",			latitude: 35.827517,		longitude: 128.528996 },
			{ id: "066",		label: "달성군",			latitude: 35.759857,		longitude: 128.498452 },
			// 인천시
			{ id: "067",		label: "중구",			latitude: 37.470598,		longitude: 126.466791 },
			{ id: "068",		label: "동구",			latitude: 37.483860,		longitude: 126.638001 },
			{ id: "069",		label: "미추홀구",		latitude: 37.445632,		longitude: 126.664876 },
			{ id: "070",		label: "연수구",			latitude: 37.389696,		longitude: 126.645726 },
			{ id: "071",		label: "남동구",			latitude: 37.430397,		longitude: 126.726539 },
			{ id: "072",		label: "부평구",			latitude: 37.492025,		longitude: 126.720813 },
			{ id: "073",		label: "계양구",			latitude: 37.557561,		longitude: 126.734483 },
			{ id: "074",		label: "서구",			latitude: 37.550238,		longitude: 126.658675 },
			{ id: "075",		label: "강화군",			latitude: 37.690473,		longitude: 126.449472 },
			{ id: "076",		label: "옹진군",			latitude: 37.369593,		longitude: 126.469983 },
			// 광주시
			{ id: "077",		label: "동구",			latitude: 35.110392,		longitude: 126.949460 },
			{ id: "078",		label: "서구",			latitude: 35.135473,		longitude: 126.851578 },
			{ id: "079",		label: "남구",			latitude: 35.085770,		longitude: 126.857270 },
			{ id: "080",		label: "북구",			latitude: 35.193480,		longitude: 126.925901 },
			{ id: "081",		label: "광산구",			latitude: 35.164483,		longitude: 126.753650 },
			// 대전시
			{ id: "082",		label: "동구",			latitude: 36.314135,		longitude: 127.465264 },
			{ id: "083",		label: "중구",			latitude: 36.280927,		longitude: 127.411167 },
			{ id: "084",		label: "서구",			latitude: 36.280236,		longitude: 127.345115 },
			{ id: "085",		label: "유성구",			latitude: 36.376961,		longitude: 127.333247 },
			{ id: "086",		label: "대덕구",			latitude: 36.400058,		longitude: 127.440427 },
			// 울산시
			{ id: "087",		label: "중구",			latitude: 35.565398,		longitude: 129.308668 },
			{ id: "088",		label: "남구",			latitude: 35.511279,		longitude: 129.325212 },
			{ id: "089",		label: "동구",			latitude: 35.520523,		longitude: 129.420748 },
			{ id: "090",		label: "북구",			latitude: 35.590759,		longitude: 129.385427 },
			{ id: "091",		label: "울주군",			latitude: 35.536366,		longitude: 129.166379 },
			// 세종시
			{ id: "092",		label: "세종시",			latitude: 36.560877,		longitude: 127.259328 },
			// 경기도
			{ id: "093",		label: "수원시",			latitude: 37.251125,		longitude: 127.009428, 		minimal: true },
			{ id: "094",		label: "성남시",			latitude: 37.407779,		longitude: 127.115162 },
			{ id: "095",		label: "고양시",			latitude: 37.665498,		longitude: 126.837820 },
			{ id: "096",		label: "용인시",			latitude: 37.221438,		longitude: 127.222036 },
			{ id: "097",		label: "부천시",			latitude: 37.485030,		longitude: 126.787230, 		minimal: true },
			{ id: "098",		label: "안산시",			latitude: 37.321630,		longitude: 126.830169, 		minimal: true },
			{ id: "099",		label: "안양시",			latitude: 37.393271,		longitude: 126.929389, 		minimal: true },
			{ id: "100",		label: "남양주시",		latitude: 37.663297,		longitude: 127.243564 },
			{ id: "101",		label: "화성시",			latitude: 37.150987,		longitude: 126.888088 },
			{ id: "102",		label: "평택시",			latitude: 37.000591,		longitude: 126.978626 },
			{ id: "103",		label: "의정부시",		latitude: 37.707978,		longitude: 127.068746 },
			{ id: "104",		label: "시흥시",			latitude: 37.379360,		longitude: 126.767793, 		minimal: true },
			{ id: "105",		label: "파주시",			latitude: 37.803804,		longitude: 126.791315 },
			{ id: "106",		label: "광명시",			latitude: 37.428211,		longitude: 126.865871,		minimal: true },
			{ id: "107",		label: "김포시",			latitude: 37.653100,		longitude: 126.611755 },
			{ id: "108",		label: "군포시",			latitude: 37.322721,		longitude: 126.922424, 		minimal: true },
			{ id: "109",		label: "광주시",			latitude: 37.402035,		longitude: 127.299439 },
			{ id: "110",		label: "이천시",			latitude: 37.209354,		longitude: 127.482136 },
			{ id: "111",		label: "양주시",			latitude: 37.808776,		longitude: 127.001152 },
			{ id: "112",		label: "오산시",			latitude: 37.163559,		longitude: 127.055136, 		minimal: true },
			{ id: "113",		label: "구리시",			latitude: 37.579016,		longitude: 127.134457, 		minimal: true },
			{ id: "114",		label: "안성시",			latitude: 37.033548,		longitude: 127.303662 },
			{ id: "115",		label: "포천시",			latitude: 37.970422,		longitude: 127.252784 },
			{ id: "116",		label: "의왕시",			latitude: 37.354956,		longitude: 126.990294, 		minimal: true },
			{ id: "117",		label: "하남시",			latitude: 37.504361,		longitude: 127.200865 },
			{ id: "118",		label: "여주시",			latitude: 37.303227,		longitude: 127.617177 },
			{ id: "119",		label: "양평군",			latitude: 37.517837,		longitude: 127.579763 },
			{ id: "120",		label: "동두천시",		latitude: 37.917615,		longitude: 127.077306 },
			{ id: "121",		label: "과천시",			latitude: 37.434269,		longitude: 127.004837, 		minimal: true },
			{ id: "122",		label: "가평군",			latitude: 37.819631,		longitude: 127.450929 },
			{ id: "123",		label: "연천군",			latitude: 38.081001,		longitude: 127.007207 },
			// 강원도
			{ id: "124",		label: "춘천시",			latitude: 37.858013,		longitude: 127.742706 },
			{ id: "125",		label: "원주시",			latitude: 37.290339,		longitude: 127.922541 },
			{ id: "126",		label: "강릉시",			latitude: 37.676306,		longitude: 128.858506 },
			{ id: "127",		label: "동해시",			latitude: 37.493009,		longitude: 129.061570 },
			{ id: "128",		label: "태백시",			latitude: 37.118344,		longitude: 128.980169 },
			{ id: "129",		label: "속초시",			latitude: 38.156608,		longitude: 128.518342 },
			{ id: "130",		label: "삼척시",			latitude: 37.254603,		longitude: 129.147971 },
			{ id: "131",		label: "홍천군",			latitude: 37.737082,		longitude: 128.073021 },
			{ id: "132",		label: "횡성군",			latitude: 37.500369,		longitude: 128.106039 },
			{ id: "133",		label: "영월군",			latitude: 37.181459,		longitude: 128.500403 },
			{ id: "134",		label: "평창군",			latitude: 37.532520,		longitude: 128.436571 },
			{ id: "135",		label: "정선군",			latitude: 37.306325,		longitude: 128.737988 },
			{ id: "136",		label: "철원군",			latitude: 38.208522,		longitude: 127.370500 },
			{ id: "137",		label: "화천군",			latitude: 38.110083,		longitude: 127.685335 },
			{ id: "138",		label: "양구군",			latitude: 38.133224,		longitude: 128.004254 },
			{ id: "139",		label: "인제군",			latitude: 38.056322,		longitude: 128.269189 },
			{ id: "140",		label: "고성군",			latitude: 38.312763,		longitude: 128.390724 },
			{ id: "141",		label: "양양군",			latitude: 37.965918,		longitude: 128.608436 },
			// 충청북도
			{ id: "142",		label: "청주시",			latitude: 36.605605,		longitude: 127.493992 },
			{ id: "143",		label: "충주시",			latitude: 37.009198,		longitude: 127.893000 },
			{ id: "144",		label: "제천시",			latitude: 37.008000,		longitude: 128.146246 },
			{ id: "145",		label: "보은군",			latitude: 36.457346,		longitude: 127.727785 },
			{ id: "146",		label: "옥천군",			latitude: 36.304768,		longitude: 127.655247 },
			{ id: "147",		label: "영동군",			latitude: 36.122076,		longitude: 127.815197 },
			{ id: "148",		label: "진천군",			latitude: 36.847395,		longitude: 127.438909 },
			{ id: "149",		label: "괴산군",			latitude: 36.750273,		longitude: 127.829832 },
			{ id: "150",		label: "음성군",			latitude: 36.956245,		longitude: 127.606553 },
			{ id: "151",		label: "단양군",			latitude: 36.962752,		longitude: 128.367842 },
			{ id: "152",		label: "증평군",			latitude: 36.781612,		longitude: 127.594628 },
			// 충청남도
			{ id: "153",		label: "천안시",			latitude: 36.778907,		longitude: 127.200718 },
			{ id: "154",		label: "공주시",			latitude: 36.459277,		longitude: 127.065344 },
			{ id: "155",		label: "보령시",			latitude: 36.305201,		longitude: 126.612228 },
			{ id: "156",		label: "아산시",			latitude: 36.784358,		longitude: 126.974983 },
			{ id: "157",		label: "서산시",			latitude: 36.742033,		longitude: 126.464090 },
			{ id: "158",		label: "논산시",			latitude: 36.161059,		longitude: 127.158532 },
			{ id: "159",		label: "계룡시",			latitude: 36.272180,		longitude: 127.232791 },
			{ id: "160",		label: "당진시",			latitude: 36.872728,		longitude: 126.665799 },
			{ id: "161",		label: "금산군",			latitude: 36.100938,		longitude: 127.478537 },
			{ id: "162",		label: "부여군",			latitude: 36.209057,		longitude: 126.860103 },
			{ id: "163",		label: "서천군",			latitude: 36.084207,		longitude: 126.718246 },
			{ id: "164",		label: "청양군",			latitude: 36.415453,		longitude: 126.855326 },
			{ id: "165",		label: "홍성군",			latitude: 36.541614,		longitude: 126.638686 },
			{ id: "166",		label: "예산군",			latitude: 36.651629,		longitude: 126.794448 },
			{ id: "167",		label: "태안군",			latitude: 36.704943,		longitude: 126.284327 },
			// 전라북도
			{ id: "168",		label: "전주시",			latitude: 35.800999,		longitude: 127.120785 },
			{ id: "169",		label: "군산시",			latitude: 35.925887,		longitude: 126.758308 },
			{ id: "170",		label: "익산시",			latitude: 36.000714,		longitude: 126.992270 },
			{ id: "171",		label: "정읍시",			latitude: 35.576274,		longitude: 126.910399 },
			{ id: "172",		label: "남원시",			latitude: 35.430358,		longitude: 127.456072 },
			{ id: "173",		label: "김제시",			latitude: 35.812191,		longitude: 126.905310 },
			{ id: "174",		label: "완주군",			latitude: 35.933621,		longitude: 127.224311 },
			{ id: "175",		label: "진안군",			latitude: 35.804653,		longitude: 127.426843 },
			{ id: "176",		label: "무주군",			latitude: 35.914167,		longitude: 127.715617 },
			{ id: "177",		label: "장수군",			latitude: 35.628272,		longitude: 127.544005 },
			{ id: "178",		label: "임실군",			latitude: 35.570082,		longitude: 127.237485 },
			{ id: "179",		label: "순창군",			latitude: 35.408951,		longitude: 127.094897 },
			{ id: "180",		label: "고창군",			latitude: 35.410796,		longitude: 126.608016 },
			{ id: "181",		label: "부안군",			latitude: 35.672663,		longitude: 126.668383 },
			// 전라남도
			{ id: "182",		label: "목포시",			latitude: 34.811069,		longitude: 126.403837 },
			{ id: "183",		label: "여수시",			latitude: 34.768018,		longitude: 127.657561 },
			{ id: "184",		label: "순천시",			latitude: 34.958249,		longitude: 127.392195 },
			{ id: "185",		label: "나주시",			latitude: 34.982703,		longitude: 126.720807 },
			{ id: "186",		label: "광양시",			latitude: 35.003903,		longitude: 127.652050 },
			{ id: "187",		label: "담양군",			latitude: 35.279132,		longitude: 126.993422 },
			{ id: "188",		label: "곡성군",			latitude: 35.203954,		longitude: 127.270886 },
			{ id: "189",		label: "구례군",			latitude: 35.222241,		longitude: 127.499975 },
			{ id: "190",		label: "고흥군",			latitude: 34.566756,		longitude: 127.331079 },
			{ id: "191",		label: "보성군",			latitude: 34.797858,		longitude: 127.163499 },
			{ id: "192",		label: "화순군",			latitude: 34.972967,		longitude: 127.038277 },
			{ id: "193",		label: "장흥군",			latitude: 34.680850,		longitude: 126.924120 },
			{ id: "194",		label: "강진군",			latitude: 34.628561,		longitude: 126.772718 },
			{ id: "195",		label: "해남군",			latitude: 34.481985,		longitude: 126.565620 },
			{ id: "196",		label: "영암군",			latitude: 34.779350,		longitude: 126.623585 },
			{ id: "197",		label: "무안군",			latitude: 34.927519,		longitude: 126.456025 },
			{ id: "198",		label: "함평군",			latitude: 35.084226,		longitude: 126.534135 },
			{ id: "199",		label: "영광군",			latitude: 35.226719,		longitude: 126.474074 },
			{ id: "200",		label: "장성군",			latitude: 35.283532,		longitude: 126.766056 },
			{ id: "201",		label: "완도군",			latitude: 34.309881,		longitude: 126.699491 },
			{ id: "202",		label: "진도군",			latitude: 34.438017,		longitude: 126.253721 },
			{ id: "203",		label: "신안군",			latitude: 34.829727,		longitude: 126.100364 },
			// 경상북도
			{ id: "204",		label: "포항시",			latitude: 36.090919,		longitude: 129.322706 },
			{ id: "205",		label: "경주시",			latitude: 35.790948,		longitude: 129.261840 },
			{ id: "206",		label: "김천시",			latitude: 36.033120,		longitude: 128.075295 },
			{ id: "207",		label: "안동시",			latitude: 36.547969,		longitude: 128.780002 },
			{ id: "208",		label: "구미시",			latitude: 36.157927,		longitude: 128.357941 },
			{ id: "209",		label: "영주시",			latitude: 36.832225,		longitude: 128.558699 },
			{ id: "210",		label: "영천시",			latitude: 35.953742,		longitude: 128.942232 },
			{ id: "211",		label: "상주시",			latitude: 36.384246,		longitude: 128.065503 },
			{ id: "212",		label: "문경시",			latitude: 36.658582,		longitude: 128.144399 },
			{ id: "213",		label: "경산시",			latitude: 35.800316,		longitude: 128.813245 },
			{ id: "214",		label: "군위군",			latitude: 36.107748,		longitude: 128.649117 },
			{ id: "215",		label: "의성군",			latitude: 36.328585,		longitude: 128.615711 },
			{ id: "216",		label: "청송군",			latitude: 36.341247,		longitude: 129.063843 },
			{ id: "217",		label: "영양군",			latitude: 36.656950,		longitude: 129.140311 },
			{ id: "218",		label: "영덕군",			latitude: 36.449030,		longitude: 129.328840 },
			{ id: "219",		label: "청도군",			latitude: 35.629976,		longitude: 128.787355 },
			{ id: "220",		label: "고령군",			latitude: 35.707112,		longitude: 128.306727 },
			{ id: "221",		label: "성주군",			latitude: 35.867813,		longitude: 128.234437 },
			{ id: "222",		label: "칠곡군",			latitude: 35.974885,		longitude: 128.462876 },
			{ id: "223",		label: "예천군",			latitude: 36.632334,		longitude: 128.427783 },
			{ id: "224",		label: "봉화군",			latitude: 36.904541,		longitude: 128.914074 },
			{ id: "225",		label: "울진군",			latitude: 36.892883,		longitude: 129.307377 },
			{ id: "226",		label: "울릉군",			latitude: 37.032701,		longitude: 129.630267 },
			// 경상남도
			{ id: "227",		label: "창원시",			latitude: 35.221073,		longitude: 128.635395 },
			{ id: "228",		label: "진주시",			latitude: 35.185506,		longitude: 128.132336 },
			{ id: "229",		label: "통영시",			latitude: 34.853442,		longitude: 128.402731 },
			{ id: "230",		label: "사천시",			latitude: 35.006951,		longitude: 128.082408 },
			{ id: "231",		label: "김해시",			latitude: 35.231385,		longitude: 128.845805 },
			{ id: "232",		label: "밀양시",			latitude: 35.435347,		longitude: 128.789369 },
			{ id: "233",		label: "거제시",			latitude: 34.822407,		longitude: 128.613785 },
			{ id: "234",		label: "양산시",			latitude: 35.370765,		longitude: 129.041821 },
			{ id: "235",		label: "의령군",			latitude: 35.371324,		longitude: 128.276974 },
			{ id: "236",		label: "함안군",			latitude: 35.239115,		longitude: 128.430678 },
			{ id: "237",		label: "창녕군",			latitude: 35.476522,		longitude: 128.492748 },
			{ id: "238",		label: "고성군",			latitude: 35.007088,		longitude: 128.293534 },
			{ id: "239",		label: "남해군",			latitude: 34.803031,		longitude: 127.894773 },
			{ id: "240",		label: "하동군",			latitude: 35.131107,		longitude: 127.794571 },
			{ id: "241",		label: "산청군",			latitude: 35.336794,		longitude: 127.889769 },
			{ id: "242",		label: "함양군",			latitude: 35.530491,		longitude: 127.725963 },
			{ id: "243",		label: "거창군",			latitude: 35.708785,		longitude: 127.914116 },
			{ id: "244",		label: "합천군",			latitude: 35.520381,		longitude: 128.142373 },
			// 제주특별자치도
			{ id: "245",		label: "제주시",			latitude: 33.427772,		longitude: 126.531205 },
			{ id: "246",		label: "서귀포시",		latitude: 33.303330,		longitude: 126.561160 }
		];
		var data_min, data_max;

		this.init = function() {
			am4core.options.autoSetClassName = true;

			$el.find( ".btn_home" ).hide();
			$el.find( ".btn_home" ).click( evt_home_click );

			getMapDataLoad();
		}

		// **********		Datas		************************************************************************************************ //
		// 지도 로드
		function getMapDataLoad() {
			$el.parents( ".ui_loader_container" ).eq( 0 ).addClass( "is-loading" );
			var url = $options.mapPaths[ parseInt( mapCode ) ].url;

			if( !_map_geo_all_data[ $options.mapPaths[ parseInt( mapCode ) ].name ] ) {
				$.ajax({
					type : "POST", 
					url : url,
					timeout : 30000,
					cache : false,
					beforeSend : function(){
						
					},
					success : function( $result ){
						_map_geo_all_data[ $options.mapPaths[ parseInt( mapCode ) ].name ] = JSON.parse( $result );
						getDataLoad();
					},
					error : function($err){
						
					}
				});
			} else {
				getDataLoad();
			}

			if( $options.evt_mapChange ) $options.evt_mapChange( mapCode );
		}
		// 데이터 로드
		function getDataLoad(){
			var url = $options.dataPath;
			var params = $options.dataParams;
			params.mapCode = mapCode;
			$.ajax({
				type : "POST", 
				url : url,
				data : params,
				timeout : 30000,
				cache : false,
				beforeSend : function(){
					
				},
				success : function( $result ){
					_map_data = JSON.parse( $result );

					// Min/Max
					data_min = _map_data.reduce( function( a, b ){
						return a < b.value ? a : b.value;
					}, _map_data[ 0 ].value );
					data_max = _map_data.reduce( function( a, b ){
						return a > b.value ? a : b.value;
					}, _map_data[ 0 ].value );

					// Range
					var len = legendDatas.length;
					var range = data_max - data_min;
					var gap = Math.floor( range / len );
					var valCnt = data_min;
					$.each( legendDatas, function( $idx ){
						this.min = valCnt + ( $idx == 0 ? 0 : 1 );
						this.max = this.min + gap;
						valCnt = this.max;
					});


					$.each( _map_data, function(){
						var tg = this;
						$.each( legendDatas, function(){
							if( tg.value >= this.min && tg.value <= this.max ) {
								tg.fill = this.fill;
								return;
							}
						});

						$.each( locations, function(){
							if( tg.id == this.id ) {
								//tg.fill = this.fill;
								tg.label = this.label;
								tg.latitude = this.latitude;
								tg.longitude = this.longitude;
								if( this.minimal ) tg.minimal = this.minimal;
								return;
							}
						});
					});

					

					setData();
				},
				error : function($err){
					
				}
			});
		}
		// 데이터 셋팅
		function setData() {
			var result = [];
			$.each( _map_geo_all_data[ $options.mapPaths[ parseInt( mapCode ) ].name ].features, function( $idx ){
				var item = {
					type: "Feature",
					geometry: {
						type: this.geometry.type,
						coordinates: this.geometry.coordinates
					},
					properties: {
						name: this.properties.name,
						id: this.properties.id,
						TYPE: "Special Self-Governing Province",
						CNTRY: "South Korea"
					},
					id: this.properties.id
				}
				result.push( item );
			});

			var map_geo_data = {
				type: "FeatureCollection",
				features: result
			}
			buildMap( map_geo_data );
		}





		// **********		Build		************************************************************************************************ //
		function buildMap( $geoData ) {
			var $chartContainer = $( "<div class=\"ui_map_chart_wrap\"></div>" );
			//$chartContainer.width( $el.width() );
			//$chartContainer.height( $el.height() );

			if( $el.find( ".ui_map_chart_wrap" ).length > 0 ) {
				if( mapLevel == 1 ) {
					TweenLite.to( $el.find( ".ui_map_chart_wrap" ).eq( 0 ), .4, { autoAlpha: 0, scale: 1.1, ease: Expo.easeIn, onComplete: function(){
						$el.find( ".ui_map_chart_wrap" ).eq( 0 ).remove();
						$el.prepend( $chartContainer );
						drawMap();
						$el.find( ".btn_home" ).show();
					}} );
				} else {
					TweenLite.to( $el.find( ".ui_map_chart_wrap" ).eq( 0 ), .4, { autoAlpha: 0, scale: 0.9, ease: Expo.easeIn, onComplete: function(){
						$el.find( ".ui_map_chart_wrap" ).eq( 0 ).remove();
						$el.prepend( $chartContainer );
						drawMap();
						$el.find( ".btn_home" ).hide();
					}} );

				}
			} else {
				$el.prepend( $chartContainer );
				drawMap();
			}

			function drawMap() {
				var map = am4core.create( $chartContainer[ 0 ], am4maps.MapChart);
				map.geodata = $geoData;
				map.chartContainer.wheelable = false;								// 휠 사용 X
				map.chartContainer.background.events.disableType("doublehit");		// 더블 클릭 X
				map.seriesContainer.draggable = false;								// 드래그 사용 X
				map.seriesContainer.resizable = false;								// 리사이즈 X
				map.seriesContainer.events.disableType("doublehit");				// 더블 클릭 X
				map.projection = new am4maps.projections.Miller();
				map.paddingTop = 40;
				map.paddingRight = 40;
				map.paddingBottom = 40;
				map.paddingLeft = 40;
				map.numberFormatter.numberFormat = "#,###.#";
				map.events.on( "ready", function( $e ){
					if( mapLevel == 1 ) {
						TweenLite.to( $chartContainer[ 0 ], 0, { autoAlpha: 0, scale: .9 } );
						TweenLite.to( $chartContainer[ 0 ], .4, { autoAlpha: 1, scale: 1, ease: Expo.easeOut } );
					} else {
						TweenLite.to( $chartContainer[ 0 ], 0, { autoAlpha: 0, scale: 1.1 } );
						TweenLite.to( $chartContainer[ 0 ], .4, { autoAlpha: 1, scale: 1, ease: Expo.easeOut } );
					}
                    $el.parents( ".ui_loader_container" ).eq( 0 ).removeClass( "is-loading" );
				});

				// 지도
				var polygonSeries = map.series.push(new am4maps.MapPolygonSeries());
				polygonSeries.useGeodata = true;
				polygonSeries.data = _map_data.concat();
				polygonSeries.calculatePercent = true;
				polygonSeries.tooltip.getFillFromObject = false;
				polygonSeries.tooltip.label.fill = am4core.color("#000000");
				polygonSeries.tooltip.background.fill = am4core.color("#ffffff");
				polygonSeries.tooltip.background.cornerRadius = 0;
				polygonSeries.tooltip.background.stroke = am4core.color( "#f5822b" );
				polygonSeries.tooltip.background.strokeOpacity = 1;
				polygonSeries.tooltip.background.strokeWidth = 2;
				polygonSeries.tooltip.pointerOrientation = "vertical";
				polygonSeries.tooltip.label.fill = am4core.color( "#666666" );
				polygonSeries.mapPolygons.template.tooltipText = "[bold]{name}[/]: {value} ({value.percent.formatNumber('#.0s')}%)";
				polygonSeries.mapPolygons.template.adapter.add( "tooltipHTML", function( $value, $target ) {
					var tooltipResult = "";
					tooltipResult += "<div class=\"chart_tooltip\">";
					tooltipResult += "<strong class=\"title\">{name}</strong>";
					tooltipResult += "<span class=\"per\">{value.percent.formatNumber('#.0')}%</span>";
					tooltipResult += "<span class=\"dv\">({value})</span>";
					tooltipResult += "</div>";
					return tooltipResult;
				});

				// 폴리곤
				var polygonTemplate = polygonSeries.mapPolygons.template;
				polygonTemplate.fill = am4core.color( "#e3e3e3" );
				polygonTemplate.propertyFields.fill = "fill";
				polygonTemplate.propertyFields.value = "value";
				polygonTemplate.cursorOverStyle = am4core.MouseCursorStyle.pointer;
				polygonTemplate.events.on( "hit", function( $e ){
					if( mapLevel == 1 ) {
                        if( $options.evt_mapSelect ) $options.evt_mapSelect( mapCode )
                        return;
                    }
					mapLevel = 1;
					var tg_data = $e.target.dataItem.dataContext;
					mapCode = tg_data.id;
					getMapDataLoad();
				});

				//if( mapLevel == 0 ) {
					var hs = polygonTemplate.states.create("hover");
					hs.properties.fill = am4core.color( "#f5822b" );
				//}

				// Marker
				var imageSeries = map.series.push( new am4maps.MapImageSeries() );
				imageSeries.interactionsEnabled  = false;
				imageSeries.data = _map_data.concat();
				var imageTemplate = imageSeries.mapImages.template;
				imageTemplate.propertyFields.longitude = "longitude";
				imageTemplate.propertyFields.latitude = "latitude";
				var image = imageTemplate.createChild(am4core.Image);
				image.width = 34;
				image.height = 43;
				image.adapter.add( "horizontalCenter", function( text, target ){
					if( target.dataItem && target.dataItem.dataContext ) {
						var title = target.dataItem.dataContext.label
						if( title.length == 4 ) {
							target.scale = 1.5;
						} else if( title.length == 3 ) {
							target.scale = 1.2;
						} else {
							target.scale = 1;
						}

						if( target.dataItem.dataContext.minimal ){
							target.scale = 0.8
						}

					}
					return "middle";
				});
				image.href = "data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAzMy43MiA0Mi4wOCI+PHBhdGggZD0iTTkyNS41MiwxMTAuNzloMGExNi44MywxNi44MywwLDAsMC02LjA4LTEyLjQxLDE3LDE3LDAsMCwwLTIzLjc4LDIuMzMsMTYuNzQsMTYuNzQsMCwwLDAtMy43OSwxMC42M2MwLC4wOCwwLC4xNSwwLC4yM2ExMy43NiwxMy43NiwwLDAsMCwuMywzYzAsLjEzLjA1LjI2LjA4LjM5YTMyLjg4LDMyLjg4LDAsMCwwLDEuNzIsNS4zMSwxNy4xNCwxNy4xNCwwLDAsMCwxMC4xMSw3Ljg2bDQuNjQsOC41OSw0LjYzLTguNTlhMTcuMTIsMTcuMTIsMCwwLDAsMTAuMTQtNy45QzkyNC45MywxMTYuNjMsOTI1Ljc5LDExMy4zMiw5MjUuNTIsMTEwLjc5WiIgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoLTg5MS44NCAtOTQuNikiIHN0eWxlPSJmaWxsOiNmZmY7b3BhY2l0eTowLjcwMDAwMDAwMDAwMDAwMDEiLz48L3N2Zz4=";
				image.verticalCenter = "bottom";
				var label = imageTemplate.createChild(am4core.Label);
				label.fill = "#333333";
				label.horizontalCenter = "middle";
				label.verticalCenter = "top";
				label.adapter.add( "text", function(text, target ) {
					var title = target.dataItem.dataContext.label
					if( title.length == 4 ) {
						target.fontSize = 11;
						target.dy = -43;
					} else if( title.length == 3 ) {
						target.fontSize = 11;
						target.dy = -36;
					} else {
						target.fontSize = 12;
						target.dy = -32;
					}

					if( target.dataItem.dataContext.minimal ){
						target.fontSize = 9;
						target.dy = -26;
					}

					return title;
				});
			}


			// Legend
			var legendContainer = am4core.create( $options.legendContainer, am4core.Container );
			legendContainer.width = am4core.percent(100);
			legendContainer.height = am4core.percent(100);

			var legend = new am4maps.Legend();
			legend.data = legendDatas;
			legend.parent = legendContainer;
			legend.position = "left";
			legend.valign = "top";
			legend.width = 100;
			legend.itemContainers.template.padding(0,0,10,0);
			legend.itemContainers.template.margin(0,0,0,0);
			legend.itemContainers.template.clickable = false;
			legend.itemContainers.template.focusable = false;
			legend.itemContainers.template.cursorOverStyle = am4core.MouseCursorStyle.default;
            legend.labels.template.text = "{min.formatNumber('#,###.#a')} ~ {max.formatNumber('#,###.#a')}";
            legend.labels.template.fontSize = 12;
			var legendMarker = legend.markers.template.children.getIndex(0);
			legendMarker.cornerRadius(0,0,0,0);
			legendMarker.strokeWidth = 0;
			legendMarker.strokeOpacity = 0;
			var legendMarkerTmpl = legend.markers.template;
			legendMarkerTmpl.width = 12;
			legendMarkerTmpl.height = 12;

			/*
			legend.numberFormatter = new am4core.NumberFormatter();
			legend.numberFormatter.numberFormat = "#.#a";

			console.log( legend.numberFormatter );
            */
		}



		// **********		Hndler		************************************************************************************************ //





		// **********		Event		************************************************************************************************ //
		function evt_home_click( $e ){
			mapCode = "000";
			mapLevel = 0;
			getMapDataLoad();
		}




		// **********		Out Method		************************************************************************************************ //
		this.set_mapCode = function( $code ){
            mapCode = $code;
            if( parseInt( $code ) == 0 ) {
                mapLevel = 0;
            } else {
                mapLevel = 1;
            }
			getMapDataLoad();
		}



	};

	$.fn.map_chart = function ( $options ) {
		return this.each(function() {
			var map_chart = new mapClass( $( this ), $options );
			$.data( this, 'map_chart', map_chart );
			$( document ).ready( map_chart.init );
		});
	}
})(jQuery);

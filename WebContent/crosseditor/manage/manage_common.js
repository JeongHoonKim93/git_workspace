var pe_TW="\x6b\x72";var pe_aoX=['\x6b\x6f','\x65\x6e','\x6a\x61','\x7a\x68\x2d\x63\x6e','\x7a\x68\x2d\x74\x77'];var pe_ix=pe_TW;function pe_bk(pe_Pi){var pe_oj="";var pe_uI="";if(navigator.userLanguage){pe_oj=navigator.userLanguage.toLowerCase();}else if(navigator.language){pe_oj=navigator.language.toLowerCase();}else{pe_oj=pe_Pi;}if(pe_oj.length>=2)pe_uI=pe_oj.substring(0,2);if(pe_uI=="")pe_uI=pe_Pi;return{'\x70\x65\x5f\x4c\x63':pe_uI,'\x70\x65\x5f\x4c\x71':pe_oj};};var pe_kC=(function(){var uat=navigator.userAgent.toLowerCase();return{IsIE:
/*@cc_on!@*/false,IsIE6:
/*@cc_on!@*/false&&(parseInt(uat.match(/msie (\d+)/)[1],10)>=6),IsIE7:
/*@cc_on!@*/false&&(parseInt(uat.match(/msie (\d+)/)[1],10)>=7),IsGecko:/gecko\//.test(uat),IsOpera: ! !window.opera,IsSafari:/applewebkit\//.test(uat)&& !/chrome\//.test(uat),IsChrome:/applewebkit\//.test(uat)&&/chrome\//.test(uat),IsMac:/macintosh/.test(uat)};})();var pe_RI=(function(){var uat=navigator.userAgent.toLowerCase();var pe_jQ="";var pe_NC=function(str){return str.replace(/(^\s*)|(\s*$)/g,'');};if(pe_kC.IsIE){pe_jQ=parseInt(uat.match(/msie (\d+)/)[1],10);if(pe_jQ>=9&&document.compatMode!="\x43\x53\x53\x31\x43\x6f\x6d\x70\x61\x74")pe_jQ=8;}else if(pe_kC.IsGecko){pe_jQ=uat.substring(uat.indexOf("\x66\x69\x72\x65\x66\x6f\x78\x2f")+8);}else if(pe_kC.IsOpera){if(uat.indexOf("\x76\x65\x72\x73\x69\x6f\x6e\x2f")!= -1){pe_jQ=pe_NC(uat.substring(uat.indexOf("\x76\x65\x72\x73\x69\x6f\x6e\x2f")+8));}else{pe_jQ=pe_NC(uat.substring(0,uat.indexOf("\x28")).replace("\x6f\x70\x65\x72\x61\x2f",""));}}else if(pe_kC.IsSafari||pe_kC.IsChrome){pe_jQ=parseInt(uat.substring(uat.indexOf("\x73\x61\x66\x61\x72\x69\x2f")+7));}return String(pe_jQ);})();var pe_hL="";var pe_fW;var pe_rF=null;var pe_Du={pe_pS:null,pe_oB:null,pe_HV:false,pe_fO:function(path,flag,async,pe_is,pe_kv){if(typeof pe_is=="\x75\x6e\x64\x65\x66\x69\x6e\x65\x64")pe_is="\x47\x45\x54";if(typeof pe_kv=="\x75\x6e\x64\x65\x66\x69\x6e\x65\x64")pe_kv=null;if(this.pe_aeW()){if(pe_is=="\x48\x45\x41\x44"){return this.pe_acE(path,flag,async,pe_is,pe_kv);}else{this.pe_acJ(path,flag,async,pe_is,pe_kv);}}else{alert("\x43\x61\x6e\x6e\x6f\x74\x20\x72\x75\x6e\x20\x43\x72\x6f\x73\x73\x45\x64\x69\x74\x6f\x72\x20\x6f\x6e\x20\x62\x72\x6f\x77\x73\x65\x72\x73\x20\x74\x68\x61\x74\x20\x64\x6f\x20\x6e\x6f\x74\x20\x73\x75\x70\x70\x6f\x72\x74\x20\x58\x4d\x4c\x48\x54\x54\x50\x2e");}},pe_aeW:function(){if(window.XMLHttpRequest){this.pe_pS=new XMLHttpRequest();}else if(window.ActiveXObject){try{this.pe_pS=new ActiveXObject("\x4d\x73\x78\x6d\x6c\x32\x2e\x58\x4d\x4c\x48\x54\x54\x50");}catch(e){try{this.pe_pS=new ActiveXObject("\x4d\x69\x63\x72\x6f\x73\x6f\x66\x74\x2e\x58\x4d\x4c\x48\x54\x54\x50");}catch(e){return false;}}}else{return false;}return true;},pe_acE:function(url,flag,async,pe_is,pe_kv){this.pe_HV=async;this.pe_oB=flag;pe_fW=this.pe_pS;try{pe_fW.open(pe_is,url,async);pe_fW.setRequestHeader("\x43\x61\x63\x68\x65\x2d\x43\x6f\x6e\x74\x72\x6f\x6c","\x6e\x6f\x2d\x63\x61\x63\x68\x65");pe_fW.setRequestHeader("\x50\x72\x61\x67\x6d\x61","\x6e\x6f\x2d\x63\x61\x63\x68\x65");pe_fW.send(pe_kv);if(pe_fW.status==200||pe_fW.status==304){if(!pe_fW.getResponseHeader(this.pe_oB)){return 0;}else{return pe_fW.getResponseHeader(this.pe_oB);}}else{return null;}}catch(e){return null;}},pe_acJ:function(url,flag,async,pe_is,pe_kv){this.pe_HV=async;this.pe_oB=flag;pe_fW=this.pe_pS;try{pe_fW.open(pe_is,url,async);if(pe_is=="\x50\x4f\x53\x54"){pe_fW.setRequestHeader("\x43\x6f\x6e\x74\x65\x6e\x74\x2d\x54\x79\x70\x65","\x61\x70\x70\x6c\x69\x63\x61\x74\x69\x6f\x6e\x2f\x78\x2d\x77\x77\x77\x2d\x66\x6f\x72\x6d\x2d\x75\x72\x6c\x65\x6e\x63\x6f\x64\x65\x64\x3b\x63\x68\x61\x72\x73\x65\x74\x3d\x55\x54\x46\x2d\x38");}else{pe_fW.setRequestHeader("\x43\x61\x63\x68\x65\x2d\x43\x6f\x6e\x74\x72\x6f\x6c","\x6e\x6f\x2d\x63\x61\x63\x68\x65");pe_fW.setRequestHeader("\x50\x72\x61\x67\x6d\x61","\x6e\x6f\x2d\x63\x61\x63\x68\x65");}if(!pe_kC.IsGecko||(pe_kC.IsGecko&&async)){if(flag=="\x58\x4d\x4c"||flag=="\x58\x53\x4c"){pe_fW.onreadystatechange=this.pe_Rn;}else{pe_fW.onreadystatechange=this.pe_Rp;}}pe_fW.send(pe_kv);if(pe_kC.IsGecko&& !async){if(flag=="\x58\x4d\x4c"||flag=="\x58\x53\x4c"){this.pe_Rn();}else{this.pe_Rp();}}}catch(e){alert(e);}},pe_Rn:function(){if(pe_fW.readyState==4){if(pe_fW.status==200||pe_fW.status==304||pe_fW.status==0){if(pe_fW.responseXML!=null){pe_Du.pe_Iv(pe_fW.responseXML);}else{alert("\x46\x61\x69\x6c\x65\x64\x20\x74\x6f\x20\x6c\x6f\x61\x64\x20\x58\x4d\x4c\x20\x66\x69\x6c\x65\x2e");}}else{alert("\x46\x61\x69\x6c\x65\x64\x20\x74\x6f\x20\x6c\x6f\x61\x64\x20\x58\x4d\x4c\x20\x66\x69\x6c\x65\x2e");}}},pe_Rp:function(){if(pe_fW.readyState==4){if(pe_fW.status==200||pe_fW.status==304||pe_fW.status==0){if(pe_fW.responseText!=null){pe_Du.pe_Iv(pe_fW.responseText);}else{alert("\x46\x61\x69\x6c\x65\x64\x20\x74\x6f\x20\x6c\x6f\x61\x64\x20\x48\x54\x4d\x4c\x20\x66\x69\x6c\x65\x2e");}}else{alert("\x46\x61\x69\x6c\x65\x64\x20\x74\x6f\x20\x6c\x6f\x61\x64\x20\x48\x54\x4d\x4c\x20\x66\x69\x6c\x65\x2e");}}},pe_Iv:function(items){pe_rF=items;pe_fW=null;}};function pe_S(pe_ama,pe_auf){for(var i=0;i<pe_ama.length;i++){if(pe_ama[i]===pe_auf){return true;}}return false;};var pe_TY=pe_bk('\x6b\x6f');if(pe_S(pe_aoX,pe_TY.pe_Lc)){pe_ix=pe_TY.pe_Lc;}else if(pe_S(pe_aoX,pe_TY.pe_Lq)){pe_ix=pe_TY.pe_Lq;}else{pe_ix="\x65\x6e";}if(pe_ix=="\x6b\x6f")pe_ix="\x6b\x72";if(typeof pe_mY!="\x75\x6e\x64\x65\x66\x69\x6e\x65\x64"&&pe_mY=="\x70\x65\x5f\x72\x77"){var pe_DV="\x2e\x2e\x2f\x2e\x2e\x2f\x6a\x73\x2f\x6c\x61\x6e\x67\x2f";if(pe_ix!=pe_TW){var pe_uE=pe_DV+pe_ix+"\x2e\x6a\x73";var pe_of=pe_Du.pe_fO(encodeURI(pe_uE),'\x43\x6f\x6e\x74\x65\x6e\x74\x2d\x4c\x65\x6e\x67\x74\x68',false,'\x48\x45\x41\x44');if(pe_of!=null){pe_DV=pe_uE;}else{if(pe_kC.IsOpera){pe_of=pe_Du.pe_fO(encodeURI(pe_uE),'\x43\x6f\x6e\x74\x65\x6e\x74\x2d\x4c\x65\x6e\x67\x74\x68',false,'\x48\x45\x41\x44');if(pe_of!=null){pe_DV=pe_uE;}else{pe_ix=pe_TW;pe_DV+=pe_ix+"\x2e\x6a\x73";}}else{pe_ix=pe_TW;pe_DV+=pe_ix+"\x2e\x6a\x73";}}}else{pe_DV+=pe_ix+"\x2e\x6a\x73";}pe_Du.pe_fO('\x2e\x2e\x2f\x2e\x2e\x2f\x63\x6f\x6e\x66\x69\x67\x2f\x68\x74\x6d\x6c\x73\x2f\x62\x6c\x61\x6e\x6b\x2e\x68\x74\x6d\x6c','\x48\x54\x4d\x4c',false);if(pe_rF!=null){if(/document.domain/i.test(pe_rF)){var pe_OZ=pe_rF.replace(/<script[^>]*>*document.domain(.*)<\/script\s*>/gi,function(a,b){pe_hL=b;});if(pe_hL!=""){if(pe_hL.indexOf("\x3b")!= -1)pe_hL=pe_hL.substring(0,pe_hL.indexOf("\x3b"));pe_hL=pe_hL.replace(/\"/g,'');pe_hL=pe_hL.replace(/\'/g,'');pe_hL=pe_hL.replace(/=/g,'');pe_hL=pe_hL.replace(/(^\s*)|(\s*$)/g,'');document.domain=pe_hL;}}}document.write('<\x73\x63\x72'+'\x69\x70\x74\x20\x74\x79\x70\x65\x3d\x22\x74\x65\x78\x74\x2f\x6a\x61\x76\x61\x73\x63\x72\x69\x70\x74\x22\x20\x73\x72\x63\x3d\x22'+pe_DV+'\x22\x3e\x3c\x2f\x73\x63\x72'+'\x69\x70\x74\x3e');}
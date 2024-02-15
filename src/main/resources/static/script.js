function getData(){
        
          return {
            beneficiaryName:document.getElementById("createbname").value,
            beneficiaryAccNo:document.getElementById("createbccno").value,
            beneficiaryBank:document.getElementById("createbank").value, 
            beneficiaryIFSC:document.getElementById("createifsc").value
          }
       }
function disappearText() {
        // Hide the text element
        document.getElementById("foundmessage").style.display = "none";
        document.getElementById("notfoundmessage").style.display = "none";
        document.getElementById("deletemessage").style.display = "none";
        document.getElementById("emptylistmessage").style.display="none";
        document.getElementById("notexistmessage").style.display="none";
        document.getElementById("notfoundidmessage").style.display="none";
    }

    // Add event listener to the document
    document.addEventListener("click", disappearText);
    
       function getUpdateData(){
        return{
          beneficiaryName:document.getElementById("updatebname").value,
          beneficiaryAccNo:document.getElementById("updatebccno").value,
          beneficiaryBank:document.getElementById("updatebank").value, 
          beneficiaryIFSC:document.getElementById("updateifsc").value
        }
       }
       function clearupdateData() {
        document.getElementById("updatebname").value="",
        document.getElementById("updatebccno").value="",
        document.getElementById("updatebank").value="",
        document.getElementById("updateifsc").value="",
        document.getElementById("updatebid").value=""
        
   }  
       function clearData() {
             document.getElementById("createbname").value="",
             document.getElementById("createbccno").value="",
             document.getElementById("createbank").value="",
             document.getElementById("createifsc").value=""
        }          

        function displayCreate(){
          document.getElementById("create").style.display="block";
          document.getElementById("update").style.display="none";
          document.getElementById("deleteall").style.display="none";
          document.getElementById("deletebyid").style.display="none";
          document.getElementById("getbyid").style.display="none";
        }
        function displayUpdate(){
          document.getElementById("update").style.display="block";
          document.getElementById("create").style.display="none";
          document.getElementById("deleteall").style.display="none";
          document.getElementById("deletebyid").style.display="none";
          document.getElementById("getbyid").style.display="none";
        }
        function displayDeleteAll(){
          document.getElementById("deleteall").style.display="block";
          document.getElementById("update").style.display="none";
          document.getElementById("create").style.display="none";
          document.getElementById("deletebyid").style.display="none";
          document.getElementById("getbyid").style.display="none";
        }

        function displayDeleteById(){
          document.getElementById("deletebyid").style.display="block";
          document.getElementById("deleteall").style.display="none";
          document.getElementById("update").style.display="none";
          document.getElementById("create").style.display="none";
          document.getElementById("getbyid").style.display="none";
        }
        function displayGetById(){
          document.getElementById("getbyid").style.display="block";
          document.getElementById("deletebyid").style.display="none";
          document.getElementById("deleteall").style.display="none";
          document.getElementById("update").style.display="none";
          document.getElementById("create").style.display="none";
        }
        function hideAll(){
          document.getElementById("getbyid").style.display="none";
          document.getElementById("deletebyid").style.display="none";
          document.getElementById("deleteall").style.display="none";
          document.getElementById("update").style.display="none";
          document.getElementById("create").style.display="none";
        }
        function hideAllErrorMsg(){
            document.getElementById("notvalidaccnum").style.display="none";
            document.getElementById("validaccnum").style.display="none";

        }
       
        async function create() {
            const displayVAN=document.getElementById("validaccnum").style.display;
            const displayNVAN=document.getElementById("notvalidaccnum").style.display;
            var tmpData="";
            try{
              var beneficiary = getData();
              console.log("beneficiaryData for create json",JSON.stringify(beneficiary));
              const response = await fetch('http://localhost:8080/api/addbeneficiary', {
              method: 'POST',
              headers: {
              'Content-Type': 'application/json'
              },
              body: JSON.stringify(beneficiary)
              });

                if (response.status===201) {
                  if(displayNVAN==="block") {
                     document.getElementById("notvalidaccnum").style.display="none";
                    } 
                  const data = await response.json();      
                  console.log('Beneficiary added:', data);
                  document.getElementById("validaccnum").innerHTML = "***Beneficiary added successfully***";
                  document.getElementById("validaccnum").style.display="block";
                  clearData();
                  tmpData+="<tr>"
                    tmpData+="<td>"+ data.id +"</td>";
                    tmpData+="<td>"+data.beneficiaryName+"</td>";
                    tmpData+="<td>"+data.beneficiaryAccNo+"</td>";
                    tmpData+="<td>"+data.beneficiaryBank+"</td>";
                    tmpData+="<td>"+data.beneficiaryIFSC+"</td>";
                 tmpData+="</tr>";
                 document.getElementById("tbData").innerHTML = tmpData;  
      
                } else if (response.status === 406) {
                    if(displayVAN==="block") {
                      document.getElementById("validaccnum").style.display="none";
                    } 
                  const errorMessage = await response.text();
                  document.getElementById("notvalidaccnum").innerHTML = errorMessage; 
                  document.getElementById("notvalidaccnum").style.display="block";
                  console.error('Error:', errorMessage);
      
                }   
    
            } catch (error) {
               console.error('Error:', error.message);
               return { error: error.message };
            }
        }


   
        async function deleteAll() {
           try {
            const response = await fetch("http://localhost:8080/api/deleteallBeneficiary", {
            method: "DELETE",                          
           });
           console.log(response.status);

              if (response.status===200) {
                console.log("All beneficiaries deleted successfully");
                getbeneficiaryList();
                document.getElementById("deletemessage").innerHTML = "All Beneficiary Data deleted successfully"; 
                document.getElementById("deletemessage").style.display="block";
               } else if(response.status === 204){
                console.log("Empty list");
                document.getElementById("deleteallbutton").disabled = true;
                document.getElementById("emptylistmessage").innerHTML = " Beneficiary List is Empty"; 
                document.getElementById("emptylistmessage").style.display="block";
               }
          } catch (error) {
        console.error("Delete request error:", error.message);
       
    }
}

async function getBeneficiaryById() {
    const displayFIDM=document.getElementById("foundidmessage").style.display;
    const displayNFIDM=document.getElementById("notfoundidmessage").style.display;
    var tmpData  = ""; 
    const id = document.forms['fetchByIdForm']['id'].value;
    try {
        const response = await fetch("http://localhost:8080/api/get-beneficiary-By-id/"+id, {
            method: "GET",
            
        });

        if (response.status===302) {
            const beneficiary = await response.json();
            console.log("Beneficiary details:", beneficiary);
            tmpData+="<tr>"
                        tmpData+="<td>"+ beneficiary.id +"</td>";
                        tmpData+="<td>"+beneficiary.beneficiaryName+"</td>";
                        tmpData+="<td>"+beneficiary.beneficiaryAccNo+"</td>";
                        tmpData+="<td>"+beneficiary.beneficiaryBank+"</td>";
                        tmpData+="<td>"+beneficiary.beneficiaryIFSC+"</td>";
                        tmpData+="</tr>";
            document.getElementById("tbData").innerHTML = tmpData;  
             if(displayNFIDM==="block") {
                document.getElementById("notfoundidmessage").style.display="none";
             }  
            } else if (response.status === 404) {
              const errormessage = await response.text();
            document.getElementById("notfoundidmessage").innerHTML = errormessage;
            document.getElementById("notfoundidmessage").style.display="block";    


            console.error("Beneficiary not found");
            
        } 
    } catch (error) {
        console.error("Fetch error:", error);
       
    }
}


async function deleteBeneficiaryById(id) {
    const displayFM=document.getElementById("foundmessage").style.display;
    const displayNFM=document.getElementById("notfoundmessage").style.display;
    const deleteid = document.forms['deleteByIdForm']['deleteid'].value;           
    try {
        const response = await fetch("http://localhost:8080/api/delete-beneficiary-By-id/"+deleteid, {
            method: "DELETE",         
            
        });

        if (response.ok) {                       
            const message = await response.text();
            console.log(message);
            console.log("displayNFM="+displayNFM); // Log the success message
            if(displayNFM==="block"){
            document.getElementById("notfoundmessage").style.display="none";
            }
            document.getElementById("foundmessage").innerHTML = message;
            document.getElementById("foundmessage").style.display="block";
            getbeneficiaryList();
         } else if (response.status === 404) {
            console.log(response.status)
            const message = await response.text();
            if(displayFM==="block"){           
            document.getElementById("foundmessage").style.display="none";
            }
            console.error("Beneficiary not found");
            document.getElementById("notfoundmessage").innerHTML = message;
            document.getElementById("notfoundmessage").style.display="block";
            
        } 
    } catch (error) {
        console.error("Delete request error:", error);
    }
}
 async function updateBeneficiary() {
  const updateid = document.forms['updateByIdForm']['updatebyid'].value; 
  const notexistMessage=document.getElementById("notexistmessage").style.display;
  var tmpData="";
  
  try {
    var beneficiaryData = getUpdateData();

    console.log("beneficiaryData",beneficiaryData);
    console.log("beneficiaryData json",JSON.stringify(beneficiaryData));
    const response = await fetch('http://localhost:8080/api/update-beneficiary/'+updateid, { 
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(beneficiaryData)
    });    
    console.log("response.ok:",response.ok)
    if (response.ok) {
      const updatedBeneficiary = await response.json();
      console.log('Updated Beneficiary:', updatedBeneficiary);
      clearupdateData();
      tmpData+="<tr>"
                    tmpData+="<td>"+ updatedBeneficiary.id +"</td>";
                    tmpData+="<td>"+updatedBeneficiary.beneficiaryName+"</td>";
                    tmpData+="<td>"+updatedBeneficiary.beneficiaryAccNo+"</td>";
                    tmpData+="<td>"+updatedBeneficiary.beneficiaryBank+"</td>";
                    tmpData+="<td>"+updatedBeneficiary.beneficiaryIFSC+"</td>";
                    tmpData+="</tr>";
            document.getElementById("tbData").innerHTML = tmpData;
      
    } else if (response.status === 404) {
      clearupdateData();
            console.log(response.status)
            const message = await response.text();
            if(notexistMessage==="none"){           
              document.getElementById("notexistmessage").innerHTML = message;
              document.getElementById("notexistmessage").style.display="block";    
            }
  }
 } catch (error) {
    console.error('Error:', error.message);
    return { error: error.message };
  }
}          


        async function getbeneficiaryList() {
            var tmpData  = "";
            const response = await fetch("http://localhost:8080/api/getAllbeneficiary", {
            method: 'GET',        
          });
          if(response.ok){
            const beneficiaryList = await response.json();
            beneficiaryList.forEach((beneficiary)=>{                                              
                        tmpData+="<tr>"
                        tmpData+="<td>"+ beneficiary.id +"</td>";
                        tmpData+="<td>"+beneficiary.beneficiaryName+"</td>";
                        tmpData+="<td>"+beneficiary.beneficiaryAccNo+"</td>";
                        tmpData+="<td>"+beneficiary.beneficiaryBank+"</td>";
                        tmpData+="<td>"+beneficiary.beneficiaryIFSC+"</td>";
                        tmpData+="</tr>";
                    })
                document.getElementById("tbData").innerHTML = tmpData;
                }
                        
            
        }
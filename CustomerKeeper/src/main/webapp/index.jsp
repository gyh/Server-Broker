<html>
<body>
<h2>Hello World!</h2>
</body>
<script src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript">
    alert("============");
    $(document).ready(function(){
        var saveDataAry=[];
        var data1={"userName":"test","address":"gz"};
        var data2={"userName":"ququ","address":"gr"};
        saveDataAry.push(data1);
        saveDataAry.push(data2);
        $.ajax({
            type:"POST",
            url:"user/addUser",
            dataType:"json",
            contentType:"application/json",
            data:JSON.stringify(saveData),
            success:function(data){

            }
        });
    });
</script>
</html>

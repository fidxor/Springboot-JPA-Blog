let index = {
    init: function () {
        $("#btn-save").on("click", () => { // function(){}, ()=>() this를 바인딩하기 위해서.
            this.save();
        });

        $("#btn-login").on("click", () => { // function(){}, ()=>() this를 바인딩하기 위해서.
            this.login();
        });

    },

    save: function () {
        // alert("user의 save함수 호출됨")
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };

        // console.log(data);

        // ajax 호출시 default가 비동기호출
        // ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
        $.ajax({
            type:"POST",
            url:"/api/user",
            data:JSON.stringify(data), // http body데이터
            contentType:"application/json; charset=utf-8", // body데이터가 어떤 타입인지
            dataType:"json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열
        }).done(function(response){
            alert("회원가입이 완료되었습니다.");
            // console.log(response);
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    login: function () {
        // alert("user의 save함수 호출됨")
        let data = {
            username: $("#username").val(),
            password: $("#password").val()
        };

        $.ajax({
            type:"POST",
            url:"/api/user/login",
            data:JSON.stringify(data), // http body데이터
            contentType:"application/json; charset=utf-8", // body데이터가 어떤 타입인지
            dataType:"json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열
        }).done(function(response){
            alert("로그인이 완료되었습니다.");
            // console.log(response);
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
}

index.init();
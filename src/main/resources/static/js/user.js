let index = {
    init: function () {
        $("#btn-join").on("click", ()=>{
            //function() {}, ()=> this를 바인딩하기 위해서!!
            this.join();
        });

        $("#btn-update").on("click", ()=>{
            //function() {}, ()=> this를 바인딩하기 위해서!!
            this.update();
        });

        // $("#btn-login").on("click", ()=>{
        //     //function() {}, ()=> this를 바인딩하기 위해서!!
        //     this.login();
        // });
    },

    join: function () {
        //alert("save()");
        let data = {
            username:$("#username").val(),
            password:$("#password").val(),
            email:$("#email").val()
        };

        //console.log(data);

        //ajax 호출시 default 가 비동기 호출
        //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
        $.ajax({
            //회원가입 수행 요청(100초 가정)
            type: "post",
            url: "/auth/join",
            data: JSON.stringify(data), //http body 데이터
            contentType: "application/json; charset=utf-8", //body 데이터가 어떤 타입인지(MIME)
            dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든것이 문자열
            //생긴게 json이라면 => javascript 오브젝트로 변환
        }).done(function (resp) {
            if (resp.status == 500) {
                alert("회원가입에 실패 하였습니다.");
            } else {
                alert("회원가입이 완료 되었습니다.");
                //console.log(resp);
                location.href = "/";
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    update: function () {
        let data = {
            id:$("#id").val(),
            username:$("#username").val(),
            password:$("#password").val(),
            email:$("#email").val()
        };

        $.ajax({
            type: "put",
            url: "/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            alert("회원수정이 완료 되었습니다.");
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    // login: function () {
    //     //alert("login()");
    //     let data = {
    //         username:$("#username").val(),
    //         password:$("#password").val()
    //     };
    //
    //     $.ajax({
    //         type: "post",
    //         url: "/api/user/login",
    //         data: JSON.stringify(data),
    //         contentType: "application/json; charset=utf-8",
    //         dataType: "json"
    //     }).done(function (resp) {
    //         alert("로그인이 완료 되었습니다.");
    //         location.href = "/";
    //     }).fail(function (error) {
    //         alert(JSON.stringify(error));
    //     });
    // }
}

index.init();
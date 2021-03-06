let index = {
    init: function () {
        $("#btn-save").on("click", ()=>{
            //function() {}, ()=> this를 바인딩하기 위해서!!
            this.save();
        });

        $("#btn-delete").on("click", ()=>{
            this.deleteById();
        });

        $("#btn-update").on("click", ()=>{
            this.update();
        });

        $("#btn-reply-save").on("click", ()=>{
            this.replySave();
        });
    },

    save: function () {
        //alert("save()");
        let data = {
            title:$("#title").val(),
            content:$("#content").val()
        };

        $.ajax({
            type: "post",
            url: "/api/board",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            alert("글쓰기가 완료 되었습니다.");
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    deleteById: function () {
       var id = $("#id").text()

        $.ajax({
            type: "delete",
            url: "/api/board/" + id,
            dataType: "json"
        }).done(function (resp) {
            alert("삭제가 완료 되었습니다.");
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    update: function () {
        let id = $("#id").val();

        let data = {
            title:$("#title").val(),
            content:$("#content").val()
        };

        $.ajax({
            type: "put",
            url: "/api/board/" + id,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            alert("수정이 완료 되었습니다.");
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    replySave: function () {
        let data = {
            userId:$("#userId").val(),
            boardId:$("#boardId").val(),
            content:$("#reply-content").val()
        };

        $.ajax({
            type: "post",
            url: '/api/board/${data.boardId}/reply',
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            alert("댓글작성이 완료 되었습니다.");
            location.href = "/board/" + data.boardId;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    replyDelete: function (boardId, replyId) {
        $.ajax({
            type: "delete",
            url: "/api/board/" + boardId + "/reply/" + replyId,
            dataType: "json"
        }).done(function (resp) {
            alert("댓글삭제 송공 하였습니다.");
            location.href = "/board/" + boardId;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
}

index.init();
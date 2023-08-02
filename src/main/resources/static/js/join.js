const joinBtn = document.getElementById('join-button');

//회원가입 제출 확인
function checkJoin() {
    let check = true;

    if (document.getElementById('userId').value.trim() == "") {
        alert("아이디를 입력해주세요");
        check = false;
    } else if (document.getElementById('pwd').value.trim() == "") {
        alert("비밀번호를 입력해주세요");
        check = false;
    } else if (document.getElementById('checkPwd').value.trim() == "") {
        alert("비밀번호 확인을 입력해주세요.")
        check = false;
    } else if (document.getElementById('pwd').value.trim() != document.getElementById('checkPwd').value.trim()) {
        alert("비밀번호와 비밀번호 확인에 실패했습니다. 다시 입력해주세요.")
        check = false;
    } else if (document.getElementById('nickName').value.trim() == "") {
        alert("닉네임을 입력해주세요.");
        check = false;
    } else if (document.getElementById('email').value.trim() == "") {
        alert("이메일을 입력해주세요.");
        check = false;
    } else if (!document.getElementById('email').readOnly) {
        alert("이메일을 인증을 해주세요.");
        check = false;
    }

    return check;
}

//이메일 인증
let sendCode;
let cnt = 0;
document.getElementById('send-code-btn').addEventListener('click', function() {
    let email = document.getElementById('email').value.trim();

    if (email == "") {
        alert("이메일을 입력해주세요.");
    } else {
        if (!document.getElementById('email').readOnly) {
            document.getElementById('error-message').style.display = 'none';
            document.getElementById('code-input-area').style.display = 'flex';

        //  이메일 코드 송신
            $.ajax({
                type : 'GET',
                url : encodeURI("/api/join/emailAuth/" + email + "/" + cnt.toString()),
                dataType : "text",
                data : {"email":email},
                success : function(result) {
                    sendCode = result;
                },
                error : function(request, status, error) { // 결과 에러 콜백함수
                    alert('코드 송신에 실패했습니다. 다시 시도해주세요.');
                }
            })
        }
    }
})

//코드 입력 확인
document.getElementById('match-code-btn').addEventListener('click', function() {
    const inputCode = document.getElementById('code-input').value;
    let email = document.getElementById('email').value.trim();

    if (sendCode != inputCode) {
            document.getElementById('code-input').value = "";
            alert('인증코드가 잘못되었습니다. 다시 입력해주세요.')
        } else {
            $.ajax({
                type : 'GET',
                url : encodeURI("/api/join/emailEnable"),
                dataType : "text",
                data : {"email":email},
                success : function(result) {
                    document.getElementById('code-input-area').style.display = 'none';
                    if (result == "true") {
                        document.getElementById('email').readOnly = true;
                        document.getElementById('email').style.backgroundColor = '#f6f8fa';
                    } else {
                        document.getElementById('error-message').style.display = 'block';
                    }
                },
                error : function(request, status, error) { // 결과 에러 콜백함수
                    alert('이메일 중복 확인에 실패했습니다. 다시 시도해주세요.');
                }
            })
    }
})
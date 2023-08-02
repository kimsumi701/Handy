// 수정하기 및 저장하기 버튼 클릭 이벤트
const editBox = document.getElementById('edit-box');
const infoBox = document.getElementById('info-box');
const deleteBox = document.getElementById('delete-box');
const editBtn = document.getElementById('edit-btn');
const saveBtn = document.getElementById('save-btn');
let editName = document.getElementById('edit-name');
let programName = document.getElementById('program-name');

editBtn.addEventListener('click', function () {
    infoBox.style.display = 'none';
    editName.value = programName.textContent;
    editBox.style.display = 'block';
})

saveBtn.addEventListener('click', function () {
    editBox.style.display = 'none';
    infoBox.style.display = 'block';
})

// 프로그램 리스트 클릭
const programList = document.getElementById('list-group');
let programId = document.getElementsByClassName('program-id');

programList.addEventListener('click', function (event) {

    if (event.target.classList.contains("list-group-item")) {
        editBox.style.display = 'none';

        // 기존 active 지우기
        Array.from(programList.children).forEach(item => {
            item.classList.remove('active');
        })

        // 선택한 아이템에 active 추가
        event.target.classList.add('active');

        programName.textContent = event.target.textContent.trim();
        // 프로그램 아이디 가져오기
        for (let i = 0; i < programId.length; i++) {
            programId[i].value = event.target.children[0].value;
        }

        infoBox.style.display = 'block';
        deleteBox.style.display = 'block';
    }
})

// 프로그램 추가 확인
const addItem = document.getElementById('add-program-input');

function returnCreate() {
    let result = true;

    if (addItem.value.trim() == "") {
        result = false;
        alert('추가할 프로그램명을 입력해주세요.')
    }

    return result;
}

// 프로그램 수정 확인
function returnPatch() {
    let result = true;

    for (let i = 0; i < programId.length; i++) {
        if (programId[i].value == "" || programId[i].value == null) {
            result = false;
        }
    }

    if (editName.value.trim() == "") {
        result = false;
        alert('저장할 프로그램명을 입력해주세요.')
    }

    return result;
}

// 프로그램 삭제 확인
function returnDelete() {
    let result = false;

    if(confirm("삭제하시겠습니까?")) {
        result = true;

        for (let i = 0; i < programId.length; i++) {
            if (programId[i].value == "" || programId[i].value == null) {
                result = false;
            }
        }

        if (programId.value.trim() == "") {
            result = false;
            alert('잘못된 접근입니다.');
        }
    }

    return result;
}
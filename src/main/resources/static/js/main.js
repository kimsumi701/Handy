// 검색 기능
//let serachText = document.getElementById('serach-text');
//let serachBtn = document.getElementById('serach-button');
//
//serachBtn.addEventListener('click', function() {
//    const text = serachText.value.trim();
//    if (text == "") {
//        alert("검색 내용을 입력해주세요.")
//    }
//});


// 메뉴버튼 클릭
let menu = document.getElementsByClassName('menu')[0];
let menuBar = document.getElementById('menu-bar');
let menuCloseBtn = document.getElementById('close-button');

// 메뉴 열기
menuBar.addEventListener('click', function () {
    menu.style.right = '0';
    menuBar.style.display = 'none';
    // 메뉴 스크롤만 가능하게
    document.body.style.overflow = 'hidden';
});

// 메뉴 닫기
menuCloseBtn.addEventListener('click', function () {
    menu.style.right = '-100%';
    menuBar.style.display = 'block';
    document.body.style.overflow = 'auto';
});

// 화면 사이즈별 메뉴 조정
window.onresize = function() {
    if (window.innerWidth >= 1260) {
        menu.style.right = '0';
    } else {
        menu.style.right = '-100%';
    }
}

// 즐겨찾기 버튼 클릭
const favoriteBtnClick = (event) => {
    let hasClass = event.target.classList.toggle('favorite-btn-toggle');
    const targetNode = event.target.parentNode.parentNode.parentNode;

    if (hasClass) {
        event.target.style.color = '#fb0e0e';
    } else {
        event.target.style.color = '#ffffff';
    }

    updateIsFavorite(targetNode);
}
document.querySelectorAll('#Favorite-button').forEach(btn => btn.addEventListener('click', favoriteBtnClick));

// 페이지 상단가기 버튼 클릭
const pageUpBtnClick = () => {
    window.scrollTo(0, 0);
}
document.getElementById('page-top-button').addEventListener('click', pageUpBtnClick);

// 편집&확인 버튼 관련 함수
// 그룹명 태그 찾기
function findGroupNameTag(target) {
    let tagList = document.querySelectorAll('#group-tag');
    let groupTag;

    tagList.forEach(tag => {
        if (tag.parentNode.parentNode.parentNode == target) {
            groupTag = tag;
        }
    })
    return groupTag;
}

// 그룹명 input 찾기
function findGroupNameInput(target) {
    let groupName = document.querySelectorAll('#group-name');
    let nameInput;

    groupName.forEach(name => {
        if (name.parentNode.parentNode.parentNode == target) {
            nameInput = name;
        }
    });

    return nameInput;
}

//편집, 저장버튼 공통 실행 함수
function commonSet(target, state) {
    // 단축키 수정 가능 상태
    document.querySelectorAll('.key-list').forEach(item => {
        if (item.parentNode.parentNode.parentNode.parentNode == target) {
            Array.from(item.children).forEach(children => {
                if (children.classList.contains('key')) {
                    children.children[0].disabled = state;
                }
            })
        }
    });

    // 단축키 설명 수정 가능 상태
    document.querySelectorAll('.key-explanation input').forEach(item => {
        if (item.parentNode.parentNode.parentNode.parentNode == target) {
            item.disabled = state;
        }
    })

    // 플러스 마이너스 버튼
    document.querySelectorAll('.edit-box-02').forEach(box => {
        if (box.parentNode.parentNode.parentNode == target) {
            if (state) {
                box.style.display = 'none';
            } else {
                box.style.display = 'block';
            }
        };
    });

    // 그룹 삭제 버튼
    document.querySelectorAll('#group-delete-button').forEach(btn => {
        if (btn.parentElement == target) {
            if (state) {
                btn.style.display = 'none'
            } else {
                btn.style.display = 'block';
            }
        }
    })

    // 확인 버튼
    if (state) {
        document.querySelectorAll('#edit-button').forEach(btn => {
            if (btn.parentElement.parentElement.parentElement == target) {
                btn.style.display = 'block';
            }
        });
    } else {
        document.querySelectorAll('.edit-box-03').forEach(box => {
            if ((box.parentElement.parentElement) == target) {
                box.style.display = 'block';
            };
        })
    }
}

// 편집 버튼
const editBtnClick = (event) => {
    event.target.style.display = 'none';
    const targetNode = event.target.parentNode.parentNode.parentNode;

    // 그룹명 수정 활성화
    let nameInput = findGroupNameInput(targetNode);
    let nameTag = findGroupNameTag(targetNode);

    nameInput.value = nameTag.textContent;
    nameTag.style.display = 'none';
    nameInput.style.display = 'block';
    // 공통 함수 모음 실행
    commonSet(targetNode, false);
}
document.querySelectorAll('#edit-button').forEach(btn => btn.addEventListener('click', editBtnClick));

// 확인 버튼 클릭
// 확인 버튼 함수
function saveSet(target) {
   // 공통 함수 모음 실행
   commonSet(target, true);

   // 추가된 단축키 input 글씨 색상
   document.querySelectorAll('.key-add-input').forEach(input => {
       let keyTarget = input.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;
       if (keyTarget == target) {
           input.style.color = '#252525';
       }
   })

   // 삭제 버튼 비활성화
   document.querySelectorAll('#delete-button').forEach(btn => {
       if (btn.parentNode.parentNode.parentNode == target) {
           btn.style.display = 'none';
       }
   });
}

// 그룹 단축키 확인
function isShortCut(target) {
    let find = true;
    let children = Array.from(target.children);

    children.forEach(div => {
        if (div.classList.contains('shortcut-box')) {
            if (div.children.length == 0) {
                find = false;
            }
        }
    })
    return find;
}

// 확인 버튼 클릭
let minusCheck = false;

const saveBtnClick = (event) => {
    const targetNode = event.target.parentNode.parentNode.parentNode;
    // 그룹명 수정 비활성화
    let nameInput = findGroupNameInput(targetNode);
    let nameTag = findGroupNameTag(targetNode);

// 단축키 추가되어 있지 않을 때 저장시 alert
    if (nameInput.value.trim() == "") {
        alert("그룹명을 입력해주세요.");
    } else if (!isShortCut(targetNode)) {
        alert("단축키를 추가해주세요.");
    } else {
        event.target.parentNode.style.display = 'none';
        minusCheck = false;

        nameTag.textContent = nameInput.value;
        nameInput.style.display = 'none';
        nameTag.style.display = 'block';

        saveSet(targetNode);
        saveGroup(targetNode);
    }
}
document.querySelectorAll('#save-button').forEach(btn => btn.addEventListener('click', saveBtnClick));

/* ------------------------- 단축키 관련 ---------------------------------------------- */
//단축키 아이디 찾기
function findShortCutId(target) {
    const id = [];

    document.querySelectorAll('#shortcut-id').forEach(item => {
         if (item.parentNode.parentNode.parentNode.parentNode.parentNode == target) {
            id.push(item.value);
         }
    });

    return id;
}

//단축키 찾기1
function findKey1(target) {
    const key1 = [];
    document.querySelectorAll('#key1').forEach(item => {
         if (item.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode == target) {
            if (item.value == "") {
                key1.push(null);
            } else {
                key1.push(item.value.trim());
            }
         }
    });
    return key1;
}

//단축키 찾기2
function findKey2(target) {
    const key2 = [];
    document.querySelectorAll('#key2').forEach(item => {
         if (item.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode == target) {
             if (item.value == "") {
                key2.push(null);
             } else {
                 key2.push(item.value.trim());
             }
         }
    });
    return key2;
}

//단축키 찾기3
function findKey3(target) {
    const key3 = [];
    document.querySelectorAll('#key3').forEach(item => {
         if (item.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode == target) {
            if (item.value == "") {
                key3.push(null);
              } else {
                key3.push(item.value.trim());
            }
         }
    });
    return key3;
}

//단축키 찾기4
function findKey4(target) {
    const key4 = [];
    document.querySelectorAll('#key4').forEach(item => {
         if (item.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode == target) {
            if (item.value == "") {
                key4.push(null);
            } else {
                key4.push(item.value.trim());
            }
         }
    });
    return key4;
}

//단축키 설명 찾기
function findExplain(target) {
    const explain = [];

    document.querySelectorAll('#explain').forEach(item => {
        if (item.parentNode.parentNode.parentNode.parentNode == target) {
            if (item.value == "") {
                explain.push(null);
            } else {
                explain.push(item.value.trim());
            }
        }
    });

    return explain;
}

/* ------------------------- 단축키 그룹 관련 ---------------------------------------------- */
//프로그램 아이디 찾기
function findProgramId(target) {
    let programId;

    document.querySelectorAll('#program-id').forEach(item => {
        if (item.parentNode.parentNode.parentNode == target) {
            if (item.value == null || item.value == "") {
                programId = document.getElementById('program-id-top').value;
            } else {
                programId = item.value;
            }
        }
    });

    return programId;
}

//그룸 아이디 찾기
function findGroupId(target) {
    let groupId;

    document.querySelectorAll('#group-id').forEach(item => {
        if (item.parentNode.parentNode.parentNode == target) {
            groupId = item.value;
        }
    });

    return groupId;
}

//그룹명 찾기
function findGroupName(target) {
    let groupName;

    document.querySelectorAll('#group-name').forEach(item => {
        if (item.parentNode.parentNode.parentNode == target) {
            groupName = item.value.trim();
        }
    });

    return groupName;
}

//즐겨찾기 유무
function findIsFavorite(target) {
    let isFavorite;

    document.querySelectorAll('#Favorite-button').forEach(item => {
        if (item.parentNode.parentNode.parentNode == target) {
            if (item.classList.contains('favorite-btn-toggle')) {
                isFavorite = true;
            } else {
                isFavorite = false;
            }
        }
    });

    return isFavorite
}

// 단축키 생성
function createShortCut(target) {
    const shortCut = [];

    for (let i = 0; i < findShortCutId(target).length; i++) {
        const shortCutDto = {
            id: findShortCutId(target)[i],
            groupId: findGroupId(target),
            key1: findKey1(target)[i],
            key2: findKey2(target)[i],
            key3: findKey3(target)[i],
            key4: findKey4(target)[i],
            explain: findExplain(target)[i]
        }
        shortCut.push(shortCutDto);
    }

    return shortCut;
}

// 그룹 생성
function createGroup(target) {
    const group = {
        id: findGroupId(target),
        programId: findProgramId(target),
        name: findGroupName(target),
        isFavorite: findIsFavorite(target),
        shortCut: createShortCut(target)
    };
    return group;
}

//그룹, 단축키 저장
function saveGroup(target, customUrl) {
    const group = createGroup(target);

    const url = "/api/shortcut/" + group.programId + "/groups";
    fetch(url, {
        method: "post",
        body: JSON.stringify(group),
        headers: {
            "Content-Type" : "application/json"
        }
    }).then(response => {
        const msg = (response.ok) ? "저장 성공했습니다." : "저장에 실패했습니다.";
        alert(msg);
        window.location.reload();
    })
}

//즐겨찾기 수정
function updateIsFavorite(target) {
    const group = createGroup(target);
    console.log(group);
    const url = "/api/shortcut/" + group.programId + "/groups";
    fetch(url, {
        method: "put",
        body: JSON.stringify(group),
        headers: {
            "Content-Type" : "application/json"
        }
    })
}

// 마이너스 버튼 클릭
const minusBtnClick = (event) => {
    const targetNode = event.target.parentNode.parentNode.parentNode.parentNode;
    minusCheck = true;

    document.querySelectorAll('#delete-button').forEach(btn => {
        if (btn.parentNode.parentNode.parentNode == targetNode) {
            btn.style.display = 'block';
        }
    });
}
document.querySelectorAll('#minus-shortcut-button').forEach(btn => btn.addEventListener('click', minusBtnClick));

// 삭제 버튼 클릭
function deleteBtnClick(event) {

    if (confirm("삭제하시겠습니까?")) {
//      해당 그룹의 모든 단축키 조회
        const shortCutList = createShortCut(event.target.parentElement.parentElement.parentElement)
        const shortCutIdList = document.querySelectorAll('#shortcut-id');
        let targetShortCut;
        let id;

//      비교할 단축키 아이디 찾기
        shortCutIdList.forEach(item => {
            if (item.parentElement.parentElement.parentElement == event.target.parentElement) {
                id = item.value;
            }
        })
        shortCutList.forEach(item => {
            if (item.id == id) {
                targetShortCut = item;
            }
        })

        const url = "/api/shortcut/" + targetShortCut.groupId + "/shortcut";
        fetch(url, {
            method: "delete",
            body: JSON.stringify(targetShortCut),
            headers: {
                "Content-Type" : "application/json"
            }
        }).then( response => {
            const msg = (response.ok) ? "단축키 삭제를 성공했습니다." : "단축키 삭제를 실패했습니다.";
            alert(msg);
            window.location.reload();
        })
    }
}
document.querySelectorAll('#delete-button').forEach(btn => btn.addEventListener('click', deleteBtnClick));

// 단축키 추가 양식
function addShortCut() {
    let addShortCut =
        `
            <div class="add-shortcut-list shortcut-list">
                <div class="add-key-box key-box">
                    <ul class="add-key-list key-list">
                        <input type="hidden" id="shortcut-id">
                        <li class="key">
                            <input class="key-add-input" type="text" id="key1" placeholder="Key1">
                        </li>
                        <li>+</li>
                        <li class="key">
                            <input class="key-add-input" type="text" id="key2" placeholder="Key2">
                        </li>
                        <li>+</li>
                        <li class="key">
                            <input class="key-add-input" type="text" id="key3" placeholder="Key3">
                        </li>
                        <li>+</li>
                        <li class="key">
                            <input class="key-add-input" type="text" id="key4" placeholder="Key4">
                        </li>
                    </ul>
                </div>
                <div class="add-key-explanation key-explanation">
                    <input type="text" id="explain" placeholder="단축키 설명을 입력해주세요">
                </div>
                <button class="fa-solid fa-xmark" id="delete-button" style="color: #252525;" onclick="deleteBtnClick(event)"></button>
            </div>
    `;
    return addShortCut;
}

// 플러스 버튼 클릭
const plusBtnClick = (event) => {
    let targetNode = event.target.parentNode.parentNode.parentNode.parentNode;
    let lastBox;

    for(let i = 0; i < document.querySelectorAll('.shortcut-box').length; i++) {
        if (listBox[i].parentNode == targetNode) {
            lastBox = listBox[i];
        };
    }

    lastBox.insertAdjacentHTML('beforeend',addShortCut());

    if (minusCheck) {
        minusBtnClick(event);
    }
}
document.querySelectorAll('#plus-shortcut-button').forEach(btn => btn.addEventListener('click', plusBtnClick));

// 그룹 추가 양식
function addGroupform(event) {
    let addHTML =
        `
            <div class="program-shortcut-section shortcut">
                <div class="program-info">
                    <div class="name-box">
                        <p id="group-tag"></p>
                        <input type="text" placeholder="그룹명" id="group-name">
                        <input type="hidden" id="program-id"">
                        <input type="hidden" id="group-id">
                        <button class="fa-solid fa-heart" id="Favorite-button" style="color: #ffffff;" onclick="favoriteBtnClick(event)"></button>
                    </div>
                    <div class="edit-box-01">
                        <button id="edit-button" onclick="editBtnClick(event)">편집</button>
                        <div class="edit-box-02">
                            <button class="fa-solid fa-plus" id="plus-shortcut-button"  style="color: #252525;" onclick="plusBtnClick(event)"></button>
                            <button class="fa-solid fa-minus" id="minus-shortcut-button"  style="color: #252525;" onclick="minusBtnClick(event)"></button>
                        </div>
                    </div>
                    <div class="edit-box-03">
                        <button id="save-button" onclick="saveBtnClick(event)">확인</button>
                    </div>
                </div>
                <div class="shortcut-box"></div>
                <button id="group-delete-button">삭제하기</button>
            </div>
        `;
    return addHTML;
}

// 추가된 그룹의 편집 버튼 활성화
function newGroupEdit() {
    editBtn = document.querySelectorAll('#edit-button');
    let lastBtn = editBtn[editBtn.length - 1];

    lastBtn.style.display = 'none';
    const targetNode = lastBtn.parentNode.parentNode.parentNode;

    // 그룹명 수정 활성화
    let nameInput = findGroupNameInput(targetNode);
    let nameTag = findGroupNameTag(targetNode);

    nameInput.value = nameTag.textContent;
    nameTag.style.display = 'none';
    nameInput.style.display = 'block';
    // 공통 함수 모음 실행
    commonSet(targetNode, false);
}

// 단축키 그룹 추가 [메인 페이지 / 단축키 페이지 구분]
let addGroup = document.getElementById('new-group-button');
if (addGroup != null) {
    addGroup.addEventListener('click', function (event) {
        let parent = event.target.parentNode;
        // 새로운 그룹 추가
        let container = document.getElementById('main');
        container.insertAdjacentHTML('beforeend', addGroupform());
        // 새로 추가된 그룹명 바로 수정할 수 있도록 input 보이기
        newGroupEdit();
        // 추가 버튼 맨 아래로 이동
        container.insertBefore(parent, null);
    });
}

// 단축키 그룹 삭제
const deleteGroup = (event) => {
    if (confirm("삭제하시겠습니까?")) {
        const group = createGroup(event.target.parentElement);
        const url = "/api/shortcut/" + group.programId + "/groups";

        fetch(url, {
            method: "delete",
            body: JSON.stringify(group),
            headers: {
                "Content-Type" : "application/json"
            }
        }).then(response => {
            const msg = (response.ok) ? "그룹을 삭제했습니다." : "그룹 삭제에 실패했습니다.";
            alert(msg);
            window.location.reload();
        })
    }
};
groupDeleteBtn = document.querySelectorAll('#group-delete-button').forEach(btn => btn.addEventListener('click', deleteGroup));




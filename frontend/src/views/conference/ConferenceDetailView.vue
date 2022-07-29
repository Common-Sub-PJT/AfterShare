<template>
    <div class="container">
        <div class="row">
            <div class="col-9">
                <div>
                    방제목
                </div>
                <div class="row">
                    <div class="col-4">
                        <img class="img-fluid rounded-3"
                            src="http://img.insight.co.kr/static/2018/07/26/700/5r387l930063zu4se350.jpg" alt="img" />
                        <div id='context-menu' class="custom-context-menu" style="display: none;">
                            <ul>
                                <li><a>신고하기</a></li>
                                <li><a>팔로우</a></li>
                                <li><a>볼륨</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-4">
                        <img class="img-fluid rounded-3"
                            src="http://img.insight.co.kr/static/2018/07/26/700/5r387l930063zu4se350.jpg" alt="img" />
                    </div>
                    <div class="col-4">
                        <img class="img-fluid rounded-3"
                            src="http://img.insight.co.kr/static/2018/07/26/700/5r387l930063zu4se350.jpg" alt="img" />
                    </div>
                    <!-- </div>
                <div class="row"> -->
                    <div class="col-4">
                        <img class="img-fluid rounded-3"
                            src="http://img.insight.co.kr/static/2018/07/26/700/5r387l930063zu4se350.jpg" alt="img" />
                    </div>
                    <div class="col-4">
                        <img @click="handleCreateContextMenu" class="img-fluid rounded-3"
                            src="http://img.insight.co.kr/static/2018/07/26/700/5r387l930063zu4se350.jpg" alt="img" />
                    </div>
                    <div class="col-4">
                        <img class="img-fluid rounded-3"
                            src="http://img.insight.co.kr/static/2018/07/26/700/5r387l930063zu4se350.jpg" alt="img" />
                    </div>
                </div>
            </div>
            <div class="col-3">
                <div class="row">
                    <div class="chat-log">
                        채팅 로그
                    </div>
                    <div class="chat-form">
                        <form @submit="chat">
                            <input type="text" class="form-control" id="chat-content" v-model="chatContent">
                            <button type="button">작성</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'ConferenceDetailView',
    components: {
    },
    data() {
        return {
            chatContent: '',
        }
    },
    methods: {
        chat(event) {
            event.preventDefault()
            const chatData = {
                chatContent: this.chatContent,
            }
        }
    }
}

function handleCreateContextMenu(event) {
    // 기본 Context Menu가 나오지 않게 차단
    event.preventDefault();

    const ctxMenu = document.getElementById('context-menu');

    // 노출 설정
    ctxMenu.style.display = 'block';

    // 위치 설정
    ctxMenu.style.top = event.pageY + 'px';
    ctxMenu.style.left = event.pageX + 'px';
}
// document.getElementById("context-menu").addEventListener("contextmenu", function handleCreateContextMenu(event) {
//     // 기본 Context Menu가 나오지 않게 차단
//     event.preventDefault();

//     const ctxMenu = document.getElementById('context-menu');

//     // 노출 설정
//     ctxMenu.style.display = 'block';

//     // 위치 설정
//     ctxMenu.style.top = event.pageY + 'px';
//     ctxMenu.style.left = event.pageX + 'px';
// });

// Context Menu 제거
function handleClearContextMenu(event) {
    const ctxMenu = document.getElementById('context-menu');

    // 노출 초기화
    ctxMenu.style.display = 'none';
    ctxMenu.style.top = null;
    ctxMenu.style.left = null;
}

// const contextElement = document.querySelector(".img-fluid")

// 이벤트 바인딩
document.addEventListener('contextmenu', handleCreateContextMenu, false);
document.addEventListener('click', handleClearContextMenu, false);

</script>

<style scoped>

.chat-log {
    height: 500px;
}

.chat-form {
    height: 100px;
}

.custom-context-menu {
    position: absolute;
    box-sizing: border-box;
    min-height: 100px;
    min-width: 200px;
    background-color: #ffffff;
    box-shadow: 0 0 1px 2px lightgrey;
}

.custom-context-menu ul {
    list-style: none;
    padding: 0;
    background-color: transparent;
}

.custom-context-menu li {
    padding: 3px 5px;
    cursor: pointer;
}

.custom-context-menu li:hover {
    background-color: #f0f0f0;
}
</style>
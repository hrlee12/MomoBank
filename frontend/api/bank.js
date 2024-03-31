import { localAxios } from "@/plugins/axios";

const local = localAxios();

function getAccountList(memberId, success, fail) {
  local.get(`/bank/my-accounts?memberId=${memberId}`).then(success).catch(fail);
}

// 예시
function deleteLike(cardId, success, fail) {
  local.delete(`/flashcard/${cardId}/favorite`).then(success).catch(fail);
}

function postLike(cardId, success, fail) {
  local.post(`/flashcard/${cardId}/favorite`).then(success).catch(fail);
}

function sendCardID(data, success, fail) {
  local.patch(`/flashcard/weight`, data).then(success).catch(fail);
}

export { getAccountList };

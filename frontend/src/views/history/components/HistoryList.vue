<template>
    <p>컴포넌트 연결 확인용 텍스트 list</p>
  <history-item></history-item>
</template>

<script>
import HistoryItem from '@/views/history/components/HistoryItem.vue'
export default {
  name: 'HistoryList',
  components: {
    HistoryItem,
 }
}
</script>

<style>

</style>
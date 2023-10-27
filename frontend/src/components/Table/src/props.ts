import type { AdaptiveConfig } from '@pureadmin/table'

export default {
  border: {
    type: Boolean,
    default: true
  },
  pagination: {
    type: Object,
    default: {
      total: 0,
      pageSize: 10,
      currentPage: 1,
      background: true,
      pageSizes: [10, 20, 50, 200]
    }
  },
  highlightCurrentRow: {
    type: Boolean,
    default: true
  },
  rowKey: {
    type: String,
    default: 'id'
  },
  pageParam: {
    type: Object,
    default: {}
  },
  pageData: {
    type: Object,
    default: {}
  },
  columns: {
    type: Array as PropType<TableColumnList>,
    default: () => []
  },
  saveKey: {
    type: String,
    default: ''
  },
  alignWhole: {
    type: String,
    default: 'center'
  },
  showOverflowTooltip: {
    type: Boolean,
    default: true
  },
  adaptiveConfig: {
    type: Object as AdaptiveConfig,
    default: { offsetBottom: 560, zIndex: 0 }
  },
  heightPer: {
    type: String || Number,
    default: '57'
  },
  adaptive: {
    type: Boolean,
    default: false
  },
  data: {
    type: Array,
    default: []
  }
}

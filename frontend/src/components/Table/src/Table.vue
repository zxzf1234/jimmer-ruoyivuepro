<!-- eslint-disable no-unused-vars -->
<script lang="tsx">
import { PureTable } from '@pureadmin/table'
import { ContextMenu } from '@imengyu/vue3-context-menu'
import { Icon } from '@/components/Icon'
import props from './props'
import Sortable from 'sortablejs'
import DragIcon from './svg/drag.svg?component'
import { cloneDeep, isBoolean, isFunction, getKeyList, debounce } from '@pureadmin/utils'
import { useTableStoreWithOut } from '@/store/modules/table'

export default defineComponent({
  name: 'Table',
  props,
  emits: ['page-change'],
  setup(props, { slots, attrs, emit }) {
    const {
      columns,
      pagination,
      saveKey,
      pageParam,
      pageData,
      data,
      adaptive,
      adaptiveConfig,
      heightPer
    } = toRefs(props)
    const lastColumnLabel = ref('')
    const tableStore = useTableStoreWithOut()
    const getDynamicColumns = () => {
      if (unref(saveKey) && tableStore?.[unref(saveKey)]?.['column']) {
        let saveColumn = tableStore[unref(saveKey)]['column']
        let currentColumns = []
        let saveLabel = getKeyList(saveColumn, 'label')
        let originLabel = getKeyList(unref(columns), 'label')
        let originColumns = cloneDeep(unref(columns))
        let y = 0
        for (let i = 0; i < saveLabel.length; i++) {
          if (originLabel.indexOf(saveLabel[i]) >= 0) {
            currentColumns[y] = originColumns[originLabel.indexOf(saveLabel[i])]
            currentColumns[y].hide = saveColumn[i].hide
            y++
          }
        }
        for (let i = 0; i < originLabel.length; i++) {
          if (saveLabel.indexOf(originLabel[i]) == -1) {
            currentColumns[y] = originColumns[i]
            y++
          }
        }
        return currentColumns
      } else return cloneDeep(unref(columns))
    }
    const dynamicColumns = ref(getDynamicColumns())
    let checkColumnList = getKeyList(cloneDeep(unref(dynamicColumns)), 'label')
    let paginationCom = computed(() => {
      if (!unref(pageParam) || Object.keys(unref(pageParam)).length == 0) return undefined
      if (unref(saveKey) && tableStore?.[unref(saveKey)]?.['pageSize']) {
        unref(pagination).pageSize = tableStore[unref(saveKey)]['pageSize']
        unref(pageParam).pageSize = tableStore[unref(saveKey)]['pageSize']
      }
      if (unref(pageData)) unref(pagination).total = Number(unref(pageData).total)
      return pagination
    })
    let dataCom = () => {
      if (Object.keys(unref(pageData)).length > 0) return unref(pageData).list
      else return unref(data)
    }
    const SettingReference = {
      reference: () => (
        <el-button class="float-right" link type="primary">
          <Icon icon="ep:setting" />
        </el-button>
      )
    }
    const filterColumns = cloneDeep(unref(dynamicColumns)).filter((column) =>
      isBoolean(column?.hide) ? !column.hide : !(isFunction(column?.hide) && column?.hide())
    )

    const checkedColumns = ref(getKeyList(cloneDeep(filterColumns), 'label'))

    /** 列展示拖拽排序 */
    const rowDrop = (event: { preventDefault: () => void }) => {
      event.preventDefault()
      nextTick(() => {
        const wrapper = document.querySelector(
          ".el-checkbox-group[savekey='" + unref(saveKey) + "']>div"
        )
        Sortable.create(wrapper, {
          animation: 300,
          handle: '.drag-btn',
          onEnd: ({ newIndex, oldIndex, item }) => {
            const targetThElem = item
            const wrapperElem = targetThElem.parentNode as HTMLElement
            const oldColumn = dynamicColumns.value[oldIndex]
            const newColumn = dynamicColumns.value[newIndex]
            if (oldColumn?.fixed || newColumn?.fixed) {
              // 当前列存在fixed属性 则不可拖拽
              const oldThElem = wrapperElem.children[oldIndex] as HTMLElement
              if (newIndex > oldIndex) {
                wrapperElem.insertBefore(targetThElem, oldThElem)
              } else {
                wrapperElem.insertBefore(
                  targetThElem,
                  oldThElem ? oldThElem.nextElementSibling : oldThElem
                )
              }
              return
            }
            const currentRow = dynamicColumns.value.splice(oldIndex, 1)[0]
            dynamicColumns.value.splice(newIndex, 0, currentRow)
            moveSettingButton()
          }
        })
      })
    }

    function handleCheckColumnListChange(val: boolean, label: string) {
      dynamicColumns.value.filter((item) => item.label === label)[0].hide = !val
      // moveSettingButton()
    }

    const isFixedColumn = (label: string) => {
      return dynamicColumns.value.filter((item) => item.label === label)[0].fixed ? true : false
    }

    const moveSettingButton = () => {
      if (!unref(saveKey)) {
        return
      }
      let currentSettingColumnIndex = 0
      let lastColumnIndex = 0
      for (let i = 0; i < unref(dynamicColumns).length; i++) {
        if (!unref(dynamicColumns)[i].hide) lastColumnIndex = i
        if (unref(dynamicColumns)[i].headerRenderer) currentSettingColumnIndex = i
      }
      if (currentSettingColumnIndex != lastColumnIndex) {
        lastColumnLabel.value = unref(dynamicColumns)[lastColumnIndex].label
        unref(dynamicColumns)[lastColumnIndex].headerRenderer = settingHeader

        unref(dynamicColumns)[currentSettingColumnIndex].headerRenderer = null
      }
      saveColumns()
    }

    const saveColumns = () => {
      if (tableStore[unref(saveKey)]) {
        let tableCache = tableStore[unref(saveKey)]
        tableCache['column'] = unref(dynamicColumns)
        tableStore.setTableCache(unref(saveKey), tableCache)
      } else {
        tableStore.setTableCache(unref(saveKey), { column: unref(dynamicColumns) })
      }
    }

    const topClass = computed(() => {
      return [
        'flex',
        'justify-between',
        'pt-[3px]',
        'px-[11px]',
        'border-b-[1px]',
        'border-solid',
        'border-[#dcdfe6]',
        'dark:border-[#303030]'
      ]
    })

    async function onColumnsReset() {
      dynamicColumns.value = cloneDeep(unref(columns))
      checkColumnList = []
      checkColumnList = await getKeyList(cloneDeep(unref(columns)), 'label')
      checkedColumns.value = getKeyList(cloneDeep(unref(columns)), 'label')
      saveColumns()
    }

    let settingHeader = () => {
      return (
        <div>
          {unref(lastColumnLabel)}
          <el-popover
            placement="bottom-start"
            width="160"
            trigger="click"
            v-slots={SettingReference}
          >
            <div class={[topClass.value]}>
              <el-button class={['m-auto']} type="primary" link onClick={() => onColumnsReset()}>
                重置
              </el-button>
            </div>

            <div class="pt-[6px] pl-[11px]">
              <el-checkbox-group saveKey={unref(saveKey)} v-model={checkedColumns.value} min={1}>
                <el-space direction="vertical" alignment="flex-start" size={0}>
                  {checkColumnList.map((item) => {
                    return (
                      <div class="flex items-center">
                        <DragIcon
                          class={[
                            'drag-btn w-[16px] mr-2',
                            isFixedColumn(item) ? '!cursor-no-drop' : '!cursor-grab'
                          ]}
                          onMouseenter={(event: { preventDefault: () => void }) => rowDrop(event)}
                        />

                        <el-checkbox
                          key={item}
                          label={item}
                          onChange={(value) => handleCheckColumnListChange(value, item)}
                        >
                          <span
                            title={item}
                            class="inline-block w-[120px] truncate hover:text-text_color_primary"
                          >
                            {item}
                          </span>
                        </el-checkbox>
                      </div>
                    )
                  })}
                </el-space>
              </el-checkbox-group>
            </div>
          </el-popover>
        </div>
      )
    }

    let columnsCom = () => {
      if (unref(saveKey)) {
        let lastColumnIndex = 0
        for (let i = 0; i < unref(dynamicColumns).length; i++) {
          if (!unref(dynamicColumns)[i].hide) lastColumnIndex = i
        }
        lastColumnLabel.value = unref(dynamicColumns)[lastColumnIndex].label
        unref(dynamicColumns)[lastColumnIndex].headerRenderer = settingHeader
      }
      return unref(dynamicColumns)
    }

    const handleSizeChange = (val) => {
      unref(pageParam).pageSize = val
      if (unref(saveKey)) {
        if (!tableStore[unref(saveKey)]) tableStore.setTableCache(unref(saveKey), { pageSize: val })
        else {
          let tableCache = tableStore[unref(saveKey)]
          tableCache['pageSize'] = val
          tableStore.setTableCache(unref(saveKey), tableCache)
        }
      }
      emit('page-change', val)
    }
    const handlePageCurrentChange = (val) => {
      unref(pageParam).pageNo = val
      emit('page-change', val)
    }
    onMounted(() => {
      nextTick(() => {
        if (unref(adaptive)) {
          unref(adaptiveConfig).offsetBottom =
            window.innerHeight - (window.innerHeight * Number(unref(heightPer))) / 100
        }
      })
      if (unref(adaptive)) {
        window.addEventListener('resize', debounceSetAdaptive)
      }
    })
    const setAdaptiveOffsetBottom = async () => {
      await nextTick()
      unref(adaptiveConfig).offsetBottom =
        window.innerHeight - (window.innerHeight * Number(unref(heightPer))) / 100
    }

    const debounceSetAdaptive = debounce(
      setAdaptiveOffsetBottom,
      unref(adaptiveConfig).timeout ?? 60
    )
    onBeforeUnmount(() => {
      if (unref(adaptive)) {
        window.removeEventListener('resize', debounceSetAdaptive)
      }
    })
    const menuOption = ref({
      show: false,
      option: { zIndex: 3, minWidth: 130, x: 500, y: 200, theme: 'flat' }
    })
    let menuSlot = slots?.['menu']?.({ row: {} })
    function showMouseMenu(row, _column, event) {
      event.preventDefault()
      const { x, y } = event
      menuOption.value.show = true
      menuOption.value.option.x = x
      menuOption.value.option.y = y
      menuSlot = slots?.['menu']?.({ row: row })
    }
    return () => (
      <>
        <PureTable
          {...props}
          {...attrs}
          columns={columnsCom()}
          data={dataCom()}
          pagination={unref(paginationCom)}
          {...adaptiveConfig}
          onPage-size-change={(val) => handleSizeChange(val)}
          onPage-current-change={(val) => handlePageCurrentChange(val)}
          onRow-contextmenu={showMouseMenu}
        >
          {slots}
        </PureTable>
        <ContextMenu v-model:show={menuOption.value.show} options={menuOption.value.option}>
          {menuSlot}
        </ContextMenu>
      </>
    )
  }
})
</script>

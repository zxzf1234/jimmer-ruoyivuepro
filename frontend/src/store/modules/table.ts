import { defineStore } from 'pinia'
import { store } from '../index'
import { CACHE_KEY, useCache } from '@/hooks/web/useCache'
const { wsCache } = useCache('sessionStorage')

export const useTableStore = defineStore('table', {
  state: (): Map<string, any> => {
    const tableMap = wsCache.get(CACHE_KEY.TABLE_CACHE)
    if (!tableMap) return new Map()
    else return wsCache.get(CACHE_KEY.TABLE_CACHE)
  },

  actions: {
    setTableCache(key: string, cache: Object) {
      let tableMap = wsCache.get(CACHE_KEY.TABLE_CACHE)
      if (!tableMap) {
        tableMap = new Map()
      }
      tableMap[key] = cache
      this[key] = cache
      wsCache.set(CACHE_KEY.TABLE_CACHE, tableMap)
    }
  }
})

export const useTableStoreWithOut = () => {
  return useTableStore(store)
}

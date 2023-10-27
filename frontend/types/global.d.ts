// export {}
import type { TableColumns } from '@pureadmin/table'
declare global {
  interface Fn<T = any> {
    (...arg: T[]): T
  }

  type Nullable<T> = T | null

  type ElRef<T extends HTMLElement = HTMLDivElement> = Nullable<T>

  type Recordable<T = any, K = string> = Record<K extends null | undefined ? string : K, T>

  type ComponentRef<T> = InstanceType<T>

  type LocaleType = 'zh-CN' | 'en'

  type AxiosHeaders =
    | 'application/json'
    | 'application/x-www-form-urlencoded'
    | 'multipart/form-data'

  type AxiosMethod = 'get' | 'post' | 'delete' | 'put' | 'GET' | 'POST' | 'DELETE' | 'PUT'

  type AxiosResponseType = 'arraybuffer' | 'blob' | 'document' | 'json' | 'text' | 'stream'

  interface AxiosConfig {
    params?: any
    data?: any
    url?: string
    method?: AxiosMethod
    headersType?: string
    responseType?: AxiosResponseType
  }

  interface IResponse<T = any> {
    code: string
    data: T extends any ? T : T & any
  }

  interface PageParam {
    pageSize?: number
    pageNo?: number
  }

  interface Tree {
    id: number
    name: string
    children?: Tree[] | any[]
  }
  /**
   *  继承 `@pureadmin/table` 的 `TableColumns` ，方便全局直接调用
   */
  interface TableColumnList extends Array<TableColumns> {}
}
/**
 * 与 `ServerConfigs` 类型不同，这里是缓存到浏览器本地存储的类型声明
 * @see {@link https://yiming_chang.gitee.io/pure-admin-doc/pages/config/#serverconfig-json}
 */
interface StorageConfigs {
  version?: string
  title?: string
  fixedHeader?: boolean
  hiddenSideBar?: boolean
  multiTagsCache?: boolean
  keepAlive?: boolean
  locale?: string
  layout?: string
  theme?: string
  darkMode?: boolean
  grey?: boolean
  weak?: boolean
  hideTabs?: boolean
  sidebarStatus?: boolean
  epThemeColor?: string
  showLogo?: boolean
  showModel?: string
  mapConfigure?: {
    amapKey?: string
    options: {
      resizeEnable?: boolean
      center?: number[]
      zoom?: number
    }
  }
  username?: string
}
/**
 * 对应 `public/serverConfig.json` 文件的类型声明
 * @see {@link https://yiming_chang.gitee.io/pure-admin-doc/pages/config/#serverconfig-json}
 */
interface ServerConfigs {
  Version?: string
  Title?: string
  FixedHeader?: boolean
  HiddenSideBar?: boolean
  MultiTagsCache?: boolean
  KeepAlive?: boolean
  Locale?: string
  Layout?: string
  Theme?: string
  DarkMode?: boolean
  Grey?: boolean
  Weak?: boolean
  HideTabs?: boolean
  SidebarStatus?: boolean
  EpThemeColor?: string
  ShowLogo?: boolean
  ShowModel?: string
  MenuArrowIconNoTransition?: boolean
  CachingAsyncRoutes?: boolean
  TooltipEffect?: Effect
  ResponsiveStorageNameSpace?: string
  MapConfigure?: {
    amapKey?: string
    options: {
      resizeEnable?: boolean
      center?: number[]
      zoom?: number
    }
  }
}

import * as React from 'react'
import { LocaleProvider } from 'antd'
import zhCN from 'antd/lib/locale-provider/zh_CN'
import styles from './UserLayout.less'

class UserLayout extends React.Component {
    render() {
        return (
            // <LocaleProvider locale={zhCN}>
                <div className={styles.userLayoutWrapper}>{this.props.children}</div>
            // </LocaleProvider>
        )
    }
}

export default UserLayout

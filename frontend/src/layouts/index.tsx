import * as React from 'react'
import { LocaleProvider, Layout } from 'antd'
import { loginRoutes } from '../../config/routes.config'
import UserLayout from './UserLayout'
import { Header, Content, Footer } from './components'
import styles from './index.less'

const BasicLayout: React.FC<any> = props => {
    const { pathname } = location
    if (loginRoutes.some(route => route.path === pathname)) {
        return <UserLayout {...props}></UserLayout>
    }

    return (
        <Layout className={styles.basicLayout}>
            <Header></Header>
            <Content>{props.children}</Content>
            {/* <Footer></Footer> */}
        </Layout>
    )
}

export default BasicLayout

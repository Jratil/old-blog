import * as React from 'react'
import { LocaleProvider, Layout, Menu, Icon, Modal, Divider } from 'antd'
import { loginRoutes } from '../../config/routes.config'
import UserLayout from './UserLayout'

class BasicLayout extends React.Component {
    render() {
        const { pathname } = location
        if (loginRoutes.some(route => route.path === pathname)) {
            return <UserLayout {...this.props}></UserLayout>
        }
        console.log(location.pathname)

        return 666
    }
}

export default BasicLayout

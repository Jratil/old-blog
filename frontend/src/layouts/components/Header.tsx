import React from 'react'
import { Layout, Menu } from 'antd'
import { MScreenFull, MLanguage } from '@/components'
import styles from '../index.less'
const { Header } = Layout

const BasicHeader: React.FC<any> = props => {
    return (
        <Header className={styles.appHeader}>
            <div className="logo" />
            <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['1']} style={{ lineHeight: '64px' }}>
                <Menu.Item key="1">nav 1</Menu.Item>
                <Menu.Item key="2">nav 2</Menu.Item>
                <Menu.Item key="3">nav 3</Menu.Item>
            </Menu>
            <ul className={styles.rightMenu}>
                <li className={styles.menuItem}>
                    <MScreenFull />
                </li>
                <li className={styles.menuItem}>
                    <MLanguage />
                </li>
            </ul>
        </Header>
    )
}

export default BasicHeader

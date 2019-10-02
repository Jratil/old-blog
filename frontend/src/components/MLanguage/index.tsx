import React from 'react'
import { Dropdown, Menu, message } from 'antd'
import { MIcon } from '@/components'

const MLanguage = props => {
    const handleClick = ({ key }) => {
        message.info('暂不支持切换语言！')
    }
    const menu = (
        <Menu onClick={handleClick}>
            <Menu.Item key="zh_cn">简体中文</Menu.Item>
            <Menu.Item key="english">English</Menu.Item>
        </Menu>
    )
    return (
        <>
            <Dropdown overlay={menu}>
                <MIcon type="language" />
            </Dropdown>
        </>
    )
}

export default MLanguage

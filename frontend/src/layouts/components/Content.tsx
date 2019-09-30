import React from 'react'
import { Layout } from 'antd'
import styles from '../index.less'

const { Content } = Layout

const BasicContent: React.FC<any> = props => {
    return <Content className={styles.appContent}>{props.children}</Content>
}

export default BasicContent

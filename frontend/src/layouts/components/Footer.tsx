import React from 'react'
import { Layout,  } from 'antd'
import styles from '../index.less'

const { Footer } = Layout

const BasicFooter: React.FC<any> = props => {
    return <Footer className={styles.appFooter} style={{ textAlign: 'center' }}>Ant Design ©2018 Created by Ant UED</Footer>
}

export default BasicFooter

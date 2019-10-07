import React, { useState, useEffect } from 'react'
import { Row, Col, Card, Icon, Avatar } from 'antd'
import { connect } from 'dva'
import { MIcon } from '@/components'
import styles from './index.less'
import { Link } from 'umi'

const { Meta } = Card

const Overview: React.FC<any> = ({ dispatch, articles }) => {

    useEffect(() => {
        dispatch({ type: 'overview/getArticle' })
    }, [])

    useEffect(() => {
        const tempArr: any[] = []
        articles.forEach(item => item.hasLike && tempArr.push(item.id))
    }, [articles])

    const handleLike = (id: number) => {
        const article = articles.find(item => item.id === id)
        const hasLike = article.hasLike
        dispatch({ type: 'overview/likeArticle', payload: { id, hasLike }, callback: () => {} })
    }

    const renderArticles = () => {
        let tempArticles: any[] = []
        articles.forEach((item, index) => {
            const tempIndex = ~~(index / 4)
            if (index % 4 === 0) tempArticles[tempIndex] = []
            tempArticles[tempIndex].push({ ...item })
        })
        return tempArticles.map((arr, index) => (
            <Row gutter={20} key={index} className={styles.contentRow}>
                {arr.map((item, articleIndex) => (
                    <Col span={6} key={item.id}>
                        <Card
                            className={styles.articleCard}
                            cover={<img alt="example" src={item.imgUrl} />}
                            actions={[
                                <div onClick={() => handleLike(item.id)} className={item.hasLike ? styles.liked : null}>
                                    <MIcon type="like" key="like" />
                                    {item.like}
                                </div>,
                                <Link type="a" to={`/article/${item.id}#comment`}>
                                    <MIcon type="comment" key="comment" />
                                    {item.comment}
                                </Link>,
                                <Link type="a" to={`/article/${item.id}`}>
                                    阅读更多
                                </Link>
                            ]}
                        >
                            <Meta avatar={<Avatar src={item.avatarUrl} />} title={item.title} description={item.desc} />
                        </Card>
                    </Col>
                ))}
            </Row>
        ))
    }
    return <div className={styles.overviewWrapper}>{renderArticles()}</div>
}

export default connect(({ overview }) => ({
    articles: overview.articles
}))(Overview)

#!/user/bin/env python
# -*- coding: utf-8 -*-
#
# Author: Xuemin Zhao (xmzhao1986@gmail.com)

import sys, json

if __name__ == '__main__':
    if len(sys.argv) != 3:
        print >> sys.stderr, 'python dump_result.py dataset result'
        sys.exit(-1)

    docs = []
    with open(sys.argv[1]) as f:
        for line in f:
            d = json.loads(line)
            docs.append(d['text'])
    clusters = []
    with open(sys.argv[2]) as f:
        for line in f:
            clusters.append(int(line))
    assert len(docs) == len(clusters)

    res = zip(docs, clusters)
    res.sort(key=lambda x:x[1])
    for e in res:
        print json.dumps({'text':e[0], 'cluster':e[1]}, ensure_ascii=False)
